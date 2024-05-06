import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISensor } from 'app/shared/model/sensor.model';
import { getEntities as getSensors } from 'app/entities/sensor/sensor.reducer';
import { IVerkeerstelling } from 'app/shared/model/verkeerstelling.model';
import { getEntity, updateEntity, createEntity, reset } from './verkeerstelling.reducer';

export const VerkeerstellingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const sensors = useAppSelector(state => state.sensor.entities);
  const verkeerstellingEntity = useAppSelector(state => state.verkeerstelling.entity);
  const loading = useAppSelector(state => state.verkeerstelling.loading);
  const updating = useAppSelector(state => state.verkeerstelling.updating);
  const updateSuccess = useAppSelector(state => state.verkeerstelling.updateSuccess);

  const handleClose = () => {
    navigate('/verkeerstelling');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getSensors({}));
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
      ...verkeerstellingEntity,
      ...values,
      gegenereerddoorSensor: sensors.find(it => it.id.toString() === values.gegenereerddoorSensor?.toString()),
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
          ...verkeerstellingEntity,
          gegenereerddoorSensor: verkeerstellingEntity?.gegenereerddoorSensor?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.verkeerstelling.home.createOrEditLabel" data-cy="VerkeerstellingCreateUpdateHeading">
            Create or edit a Verkeerstelling
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="verkeerstelling-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Aantal" id="verkeerstelling-aantal" name="aantal" data-cy="aantal" type="text" />
              <ValidatedField label="Tijdtot" id="verkeerstelling-tijdtot" name="tijdtot" data-cy="tijdtot" type="text" />
              <ValidatedField label="Tijdvanaf" id="verkeerstelling-tijdvanaf" name="tijdvanaf" data-cy="tijdvanaf" type="text" />
              <ValidatedField
                id="verkeerstelling-gegenereerddoorSensor"
                name="gegenereerddoorSensor"
                data-cy="gegenereerddoorSensor"
                label="Gegenereerddoor Sensor"
                type="select"
              >
                <option value="" key="0" />
                {sensors
                  ? sensors.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/verkeerstelling" replace color="info">
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

export default VerkeerstellingUpdate;
