import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEnkelvoudigdocument } from 'app/shared/model/enkelvoudigdocument.model';
import { getEntity, updateEntity, createEntity, reset } from './enkelvoudigdocument.reducer';

export const EnkelvoudigdocumentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const enkelvoudigdocumentEntity = useAppSelector(state => state.enkelvoudigdocument.entity);
  const loading = useAppSelector(state => state.enkelvoudigdocument.loading);
  const updating = useAppSelector(state => state.enkelvoudigdocument.updating);
  const updateSuccess = useAppSelector(state => state.enkelvoudigdocument.updateSuccess);

  const handleClose = () => {
    navigate('/enkelvoudigdocument');
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
      ...enkelvoudigdocumentEntity,
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
          ...enkelvoudigdocumentEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.enkelvoudigdocument.home.createOrEditLabel" data-cy="EnkelvoudigdocumentCreateUpdateHeading">
            Create or edit a Enkelvoudigdocument
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
                <ValidatedField name="id" required readOnly id="enkelvoudigdocument-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Bestandsnaam"
                id="enkelvoudigdocument-bestandsnaam"
                name="bestandsnaam"
                data-cy="bestandsnaam"
                type="text"
              />
              <ValidatedField
                label="Documentformaat"
                id="enkelvoudigdocument-documentformaat"
                name="documentformaat"
                data-cy="documentformaat"
                type="text"
                validate={{
                  maxLength: { value: 10, message: 'This field cannot be longer than 10 characters.' },
                }}
              />
              <ValidatedField
                label="Documentinhoud"
                id="enkelvoudigdocument-documentinhoud"
                name="documentinhoud"
                data-cy="documentinhoud"
                type="text"
              />
              <ValidatedField
                label="Documentlink"
                id="enkelvoudigdocument-documentlink"
                name="documentlink"
                data-cy="documentlink"
                type="text"
              />
              <ValidatedField
                label="Documentstatus"
                id="enkelvoudigdocument-documentstatus"
                name="documentstatus"
                data-cy="documentstatus"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Documenttaal"
                id="enkelvoudigdocument-documenttaal"
                name="documenttaal"
                data-cy="documenttaal"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Documentversie"
                id="enkelvoudigdocument-documentversie"
                name="documentversie"
                data-cy="documentversie"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/enkelvoudigdocument" replace color="info">
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

export default EnkelvoudigdocumentUpdate;
