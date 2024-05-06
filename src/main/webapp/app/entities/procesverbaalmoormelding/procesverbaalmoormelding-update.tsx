import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProcesverbaalmoormelding } from 'app/shared/model/procesverbaalmoormelding.model';
import { getEntity, updateEntity, createEntity, reset } from './procesverbaalmoormelding.reducer';

export const ProcesverbaalmoormeldingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const procesverbaalmoormeldingEntity = useAppSelector(state => state.procesverbaalmoormelding.entity);
  const loading = useAppSelector(state => state.procesverbaalmoormelding.loading);
  const updating = useAppSelector(state => state.procesverbaalmoormelding.updating);
  const updateSuccess = useAppSelector(state => state.procesverbaalmoormelding.updateSuccess);

  const handleClose = () => {
    navigate('/procesverbaalmoormelding');
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
      ...procesverbaalmoormeldingEntity,
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
          ...procesverbaalmoormeldingEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.procesverbaalmoormelding.home.createOrEditLabel" data-cy="ProcesverbaalmoormeldingCreateUpdateHeading">
            Create or edit a Procesverbaalmoormelding
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
                <ValidatedField name="id" required readOnly id="procesverbaalmoormelding-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Datum" id="procesverbaalmoormelding-datum" name="datum" data-cy="datum" type="text" />
              <ValidatedField
                label="Goedkeuring"
                id="procesverbaalmoormelding-goedkeuring"
                name="goedkeuring"
                data-cy="goedkeuring"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Opmerkingen"
                id="procesverbaalmoormelding-opmerkingen"
                name="opmerkingen"
                data-cy="opmerkingen"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/procesverbaalmoormelding" replace color="info">
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

export default ProcesverbaalmoormeldingUpdate;
