import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISensor } from 'app/shared/model/sensor.model';
import { getEntity, updateEntity, createEntity, reset } from './sensor.reducer';

export const SensorUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const sensorEntity = useAppSelector(state => state.sensor.entity);
  const loading = useAppSelector(state => state.sensor.loading);
  const updating = useAppSelector(state => state.sensor.updating);
  const updateSuccess = useAppSelector(state => state.sensor.updateSuccess);

  const handleClose = () => {
    navigate('/sensor');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
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
      ...sensorEntity,
      ...values,
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
          ...sensorEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.sensor.home.createOrEditLabel" data-cy="SensorCreateUpdateHeading">
            Create or edit a Sensor
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="sensor-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Aanleghoogte" id="sensor-aanleghoogte" name="aanleghoogte" data-cy="aanleghoogte" type="text" />
              <ValidatedField label="Elektrakast" id="sensor-elektrakast" name="elektrakast" data-cy="elektrakast" type="text" />
              <ValidatedField
                label="Frequentieomvormer"
                id="sensor-frequentieomvormer"
                name="frequentieomvormer"
                data-cy="frequentieomvormer"
                type="text"
              />
              <ValidatedField label="Hoogte" id="sensor-hoogte" name="hoogte" data-cy="hoogte" type="text" />
              <ValidatedField
                label="Jaaronderhouduitgevoerd"
                id="sensor-jaaronderhouduitgevoerd"
                name="jaaronderhouduitgevoerd"
                data-cy="jaaronderhouduitgevoerd"
                type="text"
              />
              <ValidatedField label="Leverancier" id="sensor-leverancier" name="leverancier" data-cy="leverancier" type="text" />
              <ValidatedField label="Meetpunt" id="sensor-meetpunt" name="meetpunt" data-cy="meetpunt" type="text" />
              <ValidatedField label="Plc" id="sensor-plc" name="plc" data-cy="plc" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/sensor" replace color="info">
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

export default SensorUpdate;
