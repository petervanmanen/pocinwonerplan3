import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAmbacht } from 'app/shared/model/ambacht.model';
import { getEntity, updateEntity, createEntity, reset } from './ambacht.reducer';

export const AmbachtUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const ambachtEntity = useAppSelector(state => state.ambacht.entity);
  const loading = useAppSelector(state => state.ambacht.loading);
  const updating = useAppSelector(state => state.ambacht.updating);
  const updateSuccess = useAppSelector(state => state.ambacht.updateSuccess);

  const handleClose = () => {
    navigate('/ambacht');
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
      ...ambachtEntity,
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
          ...ambachtEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.ambacht.home.createOrEditLabel" data-cy="AmbachtCreateUpdateHeading">
            Create or edit a Ambacht
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="ambacht-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Ambachtsoort" id="ambacht-ambachtsoort" name="ambachtsoort" data-cy="ambachtsoort" type="text" />
              <ValidatedField
                label="Jaarambachttot"
                id="ambacht-jaarambachttot"
                name="jaarambachttot"
                data-cy="jaarambachttot"
                type="text"
              />
              <ValidatedField
                label="Jaarambachtvanaf"
                id="ambacht-jaarambachtvanaf"
                name="jaarambachtvanaf"
                data-cy="jaarambachtvanaf"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/ambacht" replace color="info">
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

export default AmbachtUpdate;
