import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IStrooidag } from 'app/shared/model/strooidag.model';
import { getEntity, updateEntity, createEntity, reset } from './strooidag.reducer';

export const StrooidagUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const strooidagEntity = useAppSelector(state => state.strooidag.entity);
  const loading = useAppSelector(state => state.strooidag.loading);
  const updating = useAppSelector(state => state.strooidag.updating);
  const updateSuccess = useAppSelector(state => state.strooidag.updateSuccess);

  const handleClose = () => {
    navigate('/strooidag');
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
      ...strooidagEntity,
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
          ...strooidagEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.strooidag.home.createOrEditLabel" data-cy="StrooidagCreateUpdateHeading">
            Create or edit a Strooidag
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="strooidag-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Datum" id="strooidag-datum" name="datum" data-cy="datum" type="date" />
              <ValidatedField
                label="Maximumtemperatuur"
                id="strooidag-maximumtemperatuur"
                name="maximumtemperatuur"
                data-cy="maximumtemperatuur"
                type="text"
              />
              <ValidatedField
                label="Minimumtemperatuur"
                id="strooidag-minimumtemperatuur"
                name="minimumtemperatuur"
                data-cy="minimumtemperatuur"
                type="text"
              />
              <ValidatedField
                label="Tijdmaximumtemperatuur"
                id="strooidag-tijdmaximumtemperatuur"
                name="tijdmaximumtemperatuur"
                data-cy="tijdmaximumtemperatuur"
                type="text"
              />
              <ValidatedField
                label="Tijdminimumtemperatuur"
                id="strooidag-tijdminimumtemperatuur"
                name="tijdminimumtemperatuur"
                data-cy="tijdminimumtemperatuur"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/strooidag" replace color="info">
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

export default StrooidagUpdate;
