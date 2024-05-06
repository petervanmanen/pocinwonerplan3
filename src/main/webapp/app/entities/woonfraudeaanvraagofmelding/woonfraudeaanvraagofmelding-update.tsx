import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IWoonfraudeaanvraagofmelding } from 'app/shared/model/woonfraudeaanvraagofmelding.model';
import { getEntity, updateEntity, createEntity, reset } from './woonfraudeaanvraagofmelding.reducer';

export const WoonfraudeaanvraagofmeldingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const woonfraudeaanvraagofmeldingEntity = useAppSelector(state => state.woonfraudeaanvraagofmelding.entity);
  const loading = useAppSelector(state => state.woonfraudeaanvraagofmelding.loading);
  const updating = useAppSelector(state => state.woonfraudeaanvraagofmelding.updating);
  const updateSuccess = useAppSelector(state => state.woonfraudeaanvraagofmelding.updateSuccess);

  const handleClose = () => {
    navigate('/woonfraudeaanvraagofmelding');
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
      ...woonfraudeaanvraagofmeldingEntity,
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
          ...woonfraudeaanvraagofmeldingEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.woonfraudeaanvraagofmelding.home.createOrEditLabel" data-cy="WoonfraudeaanvraagofmeldingCreateUpdateHeading">
            Create or edit a Woonfraudeaanvraagofmelding
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
                <ValidatedField name="id" required readOnly id="woonfraudeaanvraagofmelding-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Adres" id="woonfraudeaanvraagofmelding-adres" name="adres" data-cy="adres" type="text" />
              <ValidatedField
                label="Categorie"
                id="woonfraudeaanvraagofmelding-categorie"
                name="categorie"
                data-cy="categorie"
                type="text"
              />
              <ValidatedField
                label="Locatieomschrijving"
                id="woonfraudeaanvraagofmelding-locatieomschrijving"
                name="locatieomschrijving"
                data-cy="locatieomschrijving"
                type="text"
              />
              <ValidatedField
                label="Meldingomschrijving"
                id="woonfraudeaanvraagofmelding-meldingomschrijving"
                name="meldingomschrijving"
                data-cy="meldingomschrijving"
                type="text"
              />
              <ValidatedField
                label="Meldingtekst"
                id="woonfraudeaanvraagofmelding-meldingtekst"
                name="meldingtekst"
                data-cy="meldingtekst"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/woonfraudeaanvraagofmelding" replace color="info">
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

export default WoonfraudeaanvraagofmeldingUpdate;
