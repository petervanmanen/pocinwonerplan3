import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVomaanvraagofmelding } from 'app/shared/model/vomaanvraagofmelding.model';
import { getEntity, updateEntity, createEntity, reset } from './vomaanvraagofmelding.reducer';

export const VomaanvraagofmeldingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const vomaanvraagofmeldingEntity = useAppSelector(state => state.vomaanvraagofmelding.entity);
  const loading = useAppSelector(state => state.vomaanvraagofmelding.loading);
  const updating = useAppSelector(state => state.vomaanvraagofmelding.updating);
  const updateSuccess = useAppSelector(state => state.vomaanvraagofmelding.updateSuccess);

  const handleClose = () => {
    navigate('/vomaanvraagofmelding');
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
      ...vomaanvraagofmeldingEntity,
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
          ...vomaanvraagofmeldingEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.vomaanvraagofmelding.home.createOrEditLabel" data-cy="VomaanvraagofmeldingCreateUpdateHeading">
            Create or edit a Vomaanvraagofmelding
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
                <ValidatedField name="id" required readOnly id="vomaanvraagofmelding-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Activiteiten"
                id="vomaanvraagofmelding-activiteiten"
                name="activiteiten"
                data-cy="activiteiten"
                type="text"
              />
              <ValidatedField label="Adres" id="vomaanvraagofmelding-adres" name="adres" data-cy="adres" type="text" />
              <ValidatedField label="Bagid" id="vomaanvraagofmelding-bagid" name="bagid" data-cy="bagid" type="text" />
              <ValidatedField
                label="Dossiernummer"
                id="vomaanvraagofmelding-dossiernummer"
                name="dossiernummer"
                data-cy="dossiernummer"
                type="text"
              />
              <ValidatedField label="Intaketype" id="vomaanvraagofmelding-intaketype" name="intaketype" data-cy="intaketype" type="text" />
              <ValidatedField
                label="Internnummer"
                id="vomaanvraagofmelding-internnummer"
                name="internnummer"
                data-cy="internnummer"
                type="text"
              />
              <ValidatedField
                label="Kadastraleaanduiding"
                id="vomaanvraagofmelding-kadastraleaanduiding"
                name="kadastraleaanduiding"
                data-cy="kadastraleaanduiding"
                type="text"
              />
              <ValidatedField label="Kenmerk" id="vomaanvraagofmelding-kenmerk" name="kenmerk" data-cy="kenmerk" type="text" />
              <ValidatedField label="Locatie" id="vomaanvraagofmelding-locatie" name="locatie" data-cy="locatie" type="text" />
              <ValidatedField
                label="Locatieomschrijving"
                id="vomaanvraagofmelding-locatieomschrijving"
                name="locatieomschrijving"
                data-cy="locatieomschrijving"
                type="text"
              />
              <ValidatedField
                label="Toelichting"
                id="vomaanvraagofmelding-toelichting"
                name="toelichting"
                data-cy="toelichting"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/vomaanvraagofmelding" replace color="info">
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

export default VomaanvraagofmeldingUpdate;
