import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITrajectactiviteit } from 'app/shared/model/trajectactiviteit.model';
import { getEntity, updateEntity, createEntity, reset } from './trajectactiviteit.reducer';

export const TrajectactiviteitUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const trajectactiviteitEntity = useAppSelector(state => state.trajectactiviteit.entity);
  const loading = useAppSelector(state => state.trajectactiviteit.loading);
  const updating = useAppSelector(state => state.trajectactiviteit.updating);
  const updateSuccess = useAppSelector(state => state.trajectactiviteit.updateSuccess);

  const handleClose = () => {
    navigate('/trajectactiviteit');
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
      ...trajectactiviteitEntity,
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
          ...trajectactiviteitEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.trajectactiviteit.home.createOrEditLabel" data-cy="TrajectactiviteitCreateUpdateHeading">
            Create or edit a Trajectactiviteit
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
                <ValidatedField name="id" required readOnly id="trajectactiviteit-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Contract" id="trajectactiviteit-contract" name="contract" data-cy="contract" type="text" />
              <ValidatedField
                label="Crediteur"
                id="trajectactiviteit-crediteur"
                name="crediteur"
                data-cy="crediteur"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField label="Datumbegin" id="trajectactiviteit-datumbegin" name="datumbegin" data-cy="datumbegin" type="date" />
              <ValidatedField label="Datumeinde" id="trajectactiviteit-datumeinde" name="datumeinde" data-cy="datumeinde" type="date" />
              <ValidatedField label="Datumstatus" id="trajectactiviteit-datumstatus" name="datumstatus" data-cy="datumstatus" type="date" />
              <ValidatedField
                label="Kinderopvang"
                id="trajectactiviteit-kinderopvang"
                name="kinderopvang"
                data-cy="kinderopvang"
                type="text"
              />
              <ValidatedField
                label="Status"
                id="trajectactiviteit-status"
                name="status"
                data-cy="status"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Succesvol"
                id="trajectactiviteit-succesvol"
                name="succesvol"
                data-cy="succesvol"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/trajectactiviteit" replace color="info">
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

export default TrajectactiviteitUpdate;
