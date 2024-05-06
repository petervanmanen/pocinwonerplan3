import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IGeweldsincident } from 'app/shared/model/geweldsincident.model';
import { getEntity, updateEntity, createEntity, reset } from './geweldsincident.reducer';

export const GeweldsincidentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const geweldsincidentEntity = useAppSelector(state => state.geweldsincident.entity);
  const loading = useAppSelector(state => state.geweldsincident.loading);
  const updating = useAppSelector(state => state.geweldsincident.updating);
  const updateSuccess = useAppSelector(state => state.geweldsincident.updateSuccess);

  const handleClose = () => {
    navigate('/geweldsincident');
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
      ...geweldsincidentEntity,
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
          ...geweldsincidentEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.geweldsincident.home.createOrEditLabel" data-cy="GeweldsincidentCreateUpdateHeading">
            Create or edit a Geweldsincident
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
                <ValidatedField name="id" required readOnly id="geweldsincident-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Datum" id="geweldsincident-datum" name="datum" data-cy="datum" type="date" />
              <ValidatedField
                label="Omschrijving"
                id="geweldsincident-omschrijving"
                name="omschrijving"
                data-cy="omschrijving"
                type="text"
              />
              <ValidatedField label="Type" id="geweldsincident-type" name="type" data-cy="type" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/geweldsincident" replace color="info">
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

export default GeweldsincidentUpdate;
