import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IKadastraleonroerendezaak } from 'app/shared/model/kadastraleonroerendezaak.model';
import { getEntity, updateEntity, createEntity, reset } from './kadastraleonroerendezaak.reducer';

export const KadastraleonroerendezaakUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const kadastraleonroerendezaakEntity = useAppSelector(state => state.kadastraleonroerendezaak.entity);
  const loading = useAppSelector(state => state.kadastraleonroerendezaak.loading);
  const updating = useAppSelector(state => state.kadastraleonroerendezaak.updating);
  const updateSuccess = useAppSelector(state => state.kadastraleonroerendezaak.updateSuccess);

  const handleClose = () => {
    navigate('/kadastraleonroerendezaak');
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
    if (values.koopsom !== undefined && typeof values.koopsom !== 'number') {
      values.koopsom = Number(values.koopsom);
    }
    if (values.landinrichtingrentebedrag !== undefined && typeof values.landinrichtingrentebedrag !== 'number') {
      values.landinrichtingrentebedrag = Number(values.landinrichtingrentebedrag);
    }

    const entity = {
      ...kadastraleonroerendezaakEntity,
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
          ...kadastraleonroerendezaakEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.kadastraleonroerendezaak.home.createOrEditLabel" data-cy="KadastraleonroerendezaakCreateUpdateHeading">
            Create or edit a Kadastraleonroerendezaak
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
                <ValidatedField name="id" required readOnly id="kadastraleonroerendezaak-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Empty" id="kadastraleonroerendezaak-empty" name="empty" data-cy="empty" type="text" />
              <ValidatedField
                label="Appartementsrechtvolgnummer"
                id="kadastraleonroerendezaak-appartementsrechtvolgnummer"
                name="appartementsrechtvolgnummer"
                data-cy="appartementsrechtvolgnummer"
                type="text"
              />
              <ValidatedField
                label="Begrenzing"
                id="kadastraleonroerendezaak-begrenzing"
                name="begrenzing"
                data-cy="begrenzing"
                type="text"
              />
              <ValidatedField
                label="Cultuurcodeonbebouwd"
                id="kadastraleonroerendezaak-cultuurcodeonbebouwd"
                name="cultuurcodeonbebouwd"
                data-cy="cultuurcodeonbebouwd"
                type="text"
              />
              <ValidatedField
                label="Datumbegingeldigheidkadastraleonroerendezaak"
                id="kadastraleonroerendezaak-datumbegingeldigheidkadastraleonroerendezaak"
                name="datumbegingeldigheidkadastraleonroerendezaak"
                data-cy="datumbegingeldigheidkadastraleonroerendezaak"
                type="date"
              />
              <ValidatedField
                label="Datumeindegeldigheidkadastraleonroerendezaak"
                id="kadastraleonroerendezaak-datumeindegeldigheidkadastraleonroerendezaak"
                name="datumeindegeldigheidkadastraleonroerendezaak"
                data-cy="datumeindegeldigheidkadastraleonroerendezaak"
                type="date"
              />
              <ValidatedField
                label="Identificatie"
                id="kadastraleonroerendezaak-identificatie"
                name="identificatie"
                data-cy="identificatie"
                type="text"
              />
              <ValidatedField
                label="Kadastralegemeente"
                id="kadastraleonroerendezaak-kadastralegemeente"
                name="kadastralegemeente"
                data-cy="kadastralegemeente"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Kadastralegemeentecode"
                id="kadastraleonroerendezaak-kadastralegemeentecode"
                name="kadastralegemeentecode"
                data-cy="kadastralegemeentecode"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField label="Koopjaar" id="kadastraleonroerendezaak-koopjaar" name="koopjaar" data-cy="koopjaar" type="text" />
              <ValidatedField label="Koopsom" id="kadastraleonroerendezaak-koopsom" name="koopsom" data-cy="koopsom" type="text" />
              <ValidatedField
                label="Landinrichtingrentebedrag"
                id="kadastraleonroerendezaak-landinrichtingrentebedrag"
                name="landinrichtingrentebedrag"
                data-cy="landinrichtingrentebedrag"
                type="text"
              />
              <ValidatedField
                label="Landinrichtingrenteeindejaar"
                id="kadastraleonroerendezaak-landinrichtingrenteeindejaar"
                name="landinrichtingrenteeindejaar"
                data-cy="landinrichtingrenteeindejaar"
                type="text"
              />
              <ValidatedField label="Ligging" id="kadastraleonroerendezaak-ligging" name="ligging" data-cy="ligging" type="text" />
              <ValidatedField
                label="Locatieomschrijving"
                id="kadastraleonroerendezaak-locatieomschrijving"
                name="locatieomschrijving"
                data-cy="locatieomschrijving"
                type="text"
              />
              <ValidatedField
                label="Oppervlakte"
                id="kadastraleonroerendezaak-oppervlakte"
                name="oppervlakte"
                data-cy="oppervlakte"
                type="text"
              />
              <ValidatedField label="Oud" id="kadastraleonroerendezaak-oud" name="oud" data-cy="oud" type="text" />
              <ValidatedField
                label="Perceelnummer"
                id="kadastraleonroerendezaak-perceelnummer"
                name="perceelnummer"
                data-cy="perceelnummer"
                type="text"
              />
              <ValidatedField label="Sectie" id="kadastraleonroerendezaak-sectie" name="sectie" data-cy="sectie" type="text" />
              <ValidatedField
                label="Valutacode"
                id="kadastraleonroerendezaak-valutacode"
                name="valutacode"
                data-cy="valutacode"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/kadastraleonroerendezaak" replace color="info">
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

export default KadastraleonroerendezaakUpdate;
