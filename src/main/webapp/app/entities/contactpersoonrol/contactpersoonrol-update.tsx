import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IContactpersoonrol } from 'app/shared/model/contactpersoonrol.model';
import { getEntity, updateEntity, createEntity, reset } from './contactpersoonrol.reducer';

export const ContactpersoonrolUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const contactpersoonrolEntity = useAppSelector(state => state.contactpersoonrol.entity);
  const loading = useAppSelector(state => state.contactpersoonrol.loading);
  const updating = useAppSelector(state => state.contactpersoonrol.updating);
  const updateSuccess = useAppSelector(state => state.contactpersoonrol.updateSuccess);

  const handleClose = () => {
    navigate('/contactpersoonrol');
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
      ...contactpersoonrolEntity,
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
          ...contactpersoonrolEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.contactpersoonrol.home.createOrEditLabel" data-cy="ContactpersoonrolCreateUpdateHeading">
            Create or edit a Contactpersoonrol
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
                <ValidatedField name="id" required readOnly id="contactpersoonrol-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Contactpersoonemailadres"
                id="contactpersoonrol-contactpersoonemailadres"
                name="contactpersoonemailadres"
                data-cy="contactpersoonemailadres"
                type="text"
              />
              <ValidatedField
                label="Contactpersoonfunctie"
                id="contactpersoonrol-contactpersoonfunctie"
                name="contactpersoonfunctie"
                data-cy="contactpersoonfunctie"
                type="text"
              />
              <ValidatedField
                label="Contactpersoonnaam"
                id="contactpersoonrol-contactpersoonnaam"
                name="contactpersoonnaam"
                data-cy="contactpersoonnaam"
                type="text"
              />
              <ValidatedField
                label="Contactpersoontelefoonnummer"
                id="contactpersoonrol-contactpersoontelefoonnummer"
                name="contactpersoontelefoonnummer"
                data-cy="contactpersoontelefoonnummer"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/contactpersoonrol" replace color="info">
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

export default ContactpersoonrolUpdate;
