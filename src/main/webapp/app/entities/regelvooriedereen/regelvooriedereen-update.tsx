import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRegelvooriedereen } from 'app/shared/model/regelvooriedereen.model';
import { getEntity, updateEntity, createEntity, reset } from './regelvooriedereen.reducer';

export const RegelvooriedereenUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const regelvooriedereenEntity = useAppSelector(state => state.regelvooriedereen.entity);
  const loading = useAppSelector(state => state.regelvooriedereen.loading);
  const updating = useAppSelector(state => state.regelvooriedereen.updating);
  const updateSuccess = useAppSelector(state => state.regelvooriedereen.updateSuccess);

  const handleClose = () => {
    navigate('/regelvooriedereen');
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
      ...regelvooriedereenEntity,
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
          ...regelvooriedereenEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.regelvooriedereen.home.createOrEditLabel" data-cy="RegelvooriedereenCreateUpdateHeading">
            Create or edit a Regelvooriedereen
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
                <ValidatedField name="id" required readOnly id="regelvooriedereen-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Activiteitregelkwalificatie"
                id="regelvooriedereen-activiteitregelkwalificatie"
                name="activiteitregelkwalificatie"
                data-cy="activiteitregelkwalificatie"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/regelvooriedereen" replace color="info">
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

export default RegelvooriedereenUpdate;
