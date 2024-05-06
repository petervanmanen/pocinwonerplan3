import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITentoonstelling } from 'app/shared/model/tentoonstelling.model';
import { getEntities as getTentoonstellings } from 'app/entities/tentoonstelling/tentoonstelling.reducer';
import { ISamensteller } from 'app/shared/model/samensteller.model';
import { getEntity, updateEntity, createEntity, reset } from './samensteller.reducer';

export const SamenstellerUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const tentoonstellings = useAppSelector(state => state.tentoonstelling.entities);
  const samenstellerEntity = useAppSelector(state => state.samensteller.entity);
  const loading = useAppSelector(state => state.samensteller.loading);
  const updating = useAppSelector(state => state.samensteller.updating);
  const updateSuccess = useAppSelector(state => state.samensteller.updateSuccess);

  const handleClose = () => {
    navigate('/samensteller');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getTentoonstellings({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }

    const entity = {
      ...samenstellerEntity,
      ...values,
      steltsamenTentoonstellings: mapIdList(values.steltsamenTentoonstellings),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...samenstellerEntity,
          steltsamenTentoonstellings: samenstellerEntity?.steltsamenTentoonstellings?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.samensteller.home.createOrEditLabel" data-cy="SamenstellerCreateUpdateHeading">
            Create or edit a Samensteller
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="samensteller-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Rol" id="samensteller-rol" name="rol" data-cy="rol" type="text" />
              <ValidatedField
                label="Steltsamen Tentoonstelling"
                id="samensteller-steltsamenTentoonstelling"
                data-cy="steltsamenTentoonstelling"
                type="select"
                multiple
                name="steltsamenTentoonstellings"
              >
                <option value="" key="0" />
                {tentoonstellings
                  ? tentoonstellings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/samensteller" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default SamenstellerUpdate;
