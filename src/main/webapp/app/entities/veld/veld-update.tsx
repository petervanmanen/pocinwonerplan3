import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBelijning } from 'app/shared/model/belijning.model';
import { getEntities as getBelijnings } from 'app/entities/belijning/belijning.reducer';
import { ISportpark } from 'app/shared/model/sportpark.model';
import { getEntities as getSportparks } from 'app/entities/sportpark/sportpark.reducer';
import { IVeld } from 'app/shared/model/veld.model';
import { getEntity, updateEntity, createEntity, reset } from './veld.reducer';

export const VeldUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const belijnings = useAppSelector(state => state.belijning.entities);
  const sportparks = useAppSelector(state => state.sportpark.entities);
  const veldEntity = useAppSelector(state => state.veld.entity);
  const loading = useAppSelector(state => state.veld.loading);
  const updating = useAppSelector(state => state.veld.updating);
  const updateSuccess = useAppSelector(state => state.veld.updateSuccess);

  const handleClose = () => {
    navigate('/veld');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getBelijnings({}));
    dispatch(getSportparks({}));
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
      ...veldEntity,
      ...values,
      heeftBelijnings: mapIdList(values.heeftBelijnings),
      heeftSportpark: sportparks.find(it => it.id.toString() === values.heeftSportpark?.toString()),
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
          ...veldEntity,
          heeftBelijnings: veldEntity?.heeftBelijnings?.map(e => e.id.toString()),
          heeftSportpark: veldEntity?.heeftSportpark?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.veld.home.createOrEditLabel" data-cy="VeldCreateUpdateHeading">
            Create or edit a Veld
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="veld-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Heeft Belijning"
                id="veld-heeftBelijning"
                data-cy="heeftBelijning"
                type="select"
                multiple
                name="heeftBelijnings"
              >
                <option value="" key="0" />
                {belijnings
                  ? belijnings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="veld-heeftSportpark" name="heeftSportpark" data-cy="heeftSportpark" label="Heeft Sportpark" type="select">
                <option value="" key="0" />
                {sportparks
                  ? sportparks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/veld" replace color="info">
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

export default VeldUpdate;
