import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IReisdocumentsoort } from 'app/shared/model/reisdocumentsoort.model';
import { getEntity, updateEntity, createEntity, reset } from './reisdocumentsoort.reducer';

export const ReisdocumentsoortUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const reisdocumentsoortEntity = useAppSelector(state => state.reisdocumentsoort.entity);
  const loading = useAppSelector(state => state.reisdocumentsoort.loading);
  const updating = useAppSelector(state => state.reisdocumentsoort.updating);
  const updateSuccess = useAppSelector(state => state.reisdocumentsoort.updateSuccess);

  const handleClose = () => {
    navigate('/reisdocumentsoort');
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
      ...reisdocumentsoortEntity,
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
          ...reisdocumentsoortEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.reisdocumentsoort.home.createOrEditLabel" data-cy="ReisdocumentsoortCreateUpdateHeading">
            Create or edit a Reisdocumentsoort
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
                <ValidatedField name="id" required readOnly id="reisdocumentsoort-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Datumbegingeldigheidreisdocumentsoort"
                id="reisdocumentsoort-datumbegingeldigheidreisdocumentsoort"
                name="datumbegingeldigheidreisdocumentsoort"
                data-cy="datumbegingeldigheidreisdocumentsoort"
                type="date"
              />
              <ValidatedField
                label="Datumeindegeldigheidreisdocumentsoort"
                id="reisdocumentsoort-datumeindegeldigheidreisdocumentsoort"
                name="datumeindegeldigheidreisdocumentsoort"
                data-cy="datumeindegeldigheidreisdocumentsoort"
                type="date"
              />
              <ValidatedField
                label="Reisdocumentcode"
                id="reisdocumentsoort-reisdocumentcode"
                name="reisdocumentcode"
                data-cy="reisdocumentcode"
                type="text"
              />
              <ValidatedField
                label="Reisdocumentomschrijving"
                id="reisdocumentsoort-reisdocumentomschrijving"
                name="reisdocumentomschrijving"
                data-cy="reisdocumentomschrijving"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/reisdocumentsoort" replace color="info">
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

export default ReisdocumentsoortUpdate;
