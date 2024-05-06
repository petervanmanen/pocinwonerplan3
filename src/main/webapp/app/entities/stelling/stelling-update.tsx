import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDepot } from 'app/shared/model/depot.model';
import { getEntities as getDepots } from 'app/entities/depot/depot.reducer';
import { IStelling } from 'app/shared/model/stelling.model';
import { getEntity, updateEntity, createEntity, reset } from './stelling.reducer';

export const StellingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const depots = useAppSelector(state => state.depot.entities);
  const stellingEntity = useAppSelector(state => state.stelling.entity);
  const loading = useAppSelector(state => state.stelling.loading);
  const updating = useAppSelector(state => state.stelling.updating);
  const updateSuccess = useAppSelector(state => state.stelling.updateSuccess);

  const handleClose = () => {
    navigate('/stelling');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getDepots({}));
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
      ...stellingEntity,
      ...values,
      heeftDepot: depots.find(it => it.id.toString() === values.heeftDepot?.toString()),
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
          ...stellingEntity,
          heeftDepot: stellingEntity?.heeftDepot?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.stelling.home.createOrEditLabel" data-cy="StellingCreateUpdateHeading">
            Create or edit a Stelling
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="stelling-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Inhoud" id="stelling-inhoud" name="inhoud" data-cy="inhoud" type="text" />
              <ValidatedField label="Stellingcode" id="stelling-stellingcode" name="stellingcode" data-cy="stellingcode" type="text" />
              <ValidatedField id="stelling-heeftDepot" name="heeftDepot" data-cy="heeftDepot" label="Heeft Depot" type="select">
                <option value="" key="0" />
                {depots
                  ? depots.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/stelling" replace color="info">
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

export default StellingUpdate;
