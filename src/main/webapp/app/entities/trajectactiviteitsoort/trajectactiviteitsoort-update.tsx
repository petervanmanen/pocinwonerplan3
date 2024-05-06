import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITrajectactiviteitsoort } from 'app/shared/model/trajectactiviteitsoort.model';
import { getEntity, updateEntity, createEntity, reset } from './trajectactiviteitsoort.reducer';

export const TrajectactiviteitsoortUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const trajectactiviteitsoortEntity = useAppSelector(state => state.trajectactiviteitsoort.entity);
  const loading = useAppSelector(state => state.trajectactiviteitsoort.loading);
  const updating = useAppSelector(state => state.trajectactiviteitsoort.updating);
  const updateSuccess = useAppSelector(state => state.trajectactiviteitsoort.updateSuccess);

  const handleClose = () => {
    navigate('/trajectactiviteitsoort');
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
      ...trajectactiviteitsoortEntity,
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
          ...trajectactiviteitsoortEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.trajectactiviteitsoort.home.createOrEditLabel" data-cy="TrajectactiviteitsoortCreateUpdateHeading">
            Create or edit a Trajectactiviteitsoort
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
                <ValidatedField name="id" required readOnly id="trajectactiviteitsoort-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Aanleverensrg"
                id="trajectactiviteitsoort-aanleverensrg"
                name="aanleverensrg"
                data-cy="aanleverensrg"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Omschrijving"
                id="trajectactiviteitsoort-omschrijving"
                name="omschrijving"
                data-cy="omschrijving"
                type="text"
              />
              <ValidatedField
                label="Typetrajectsrg"
                id="trajectactiviteitsoort-typetrajectsrg"
                name="typetrajectsrg"
                data-cy="typetrajectsrg"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/trajectactiviteitsoort" replace color="info">
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

export default TrajectactiviteitsoortUpdate;
