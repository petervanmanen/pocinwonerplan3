import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { INatuurlijkpersoon } from 'app/shared/model/natuurlijkpersoon.model';
import { getEntity, updateEntity, createEntity, reset } from './natuurlijkpersoon.reducer';

export const NatuurlijkpersoonUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const natuurlijkpersoonEntity = useAppSelector(state => state.natuurlijkpersoon.entity);
  const loading = useAppSelector(state => state.natuurlijkpersoon.loading);
  const updating = useAppSelector(state => state.natuurlijkpersoon.updating);
  const updateSuccess = useAppSelector(state => state.natuurlijkpersoon.updateSuccess);

  const handleClose = () => {
    navigate('/natuurlijkpersoon');
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
      ...natuurlijkpersoonEntity,
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
          ...natuurlijkpersoonEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.natuurlijkpersoon.home.createOrEditLabel" data-cy="NatuurlijkpersoonCreateUpdateHeading">
            Create or edit a Natuurlijkpersoon
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
                <ValidatedField name="id" required readOnly id="natuurlijkpersoon-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Empty" id="natuurlijkpersoon-empty" name="empty" data-cy="empty" type="text" />
              <ValidatedField
                label="Aanduidingbijzondernederlanderschappersoon"
                id="natuurlijkpersoon-aanduidingbijzondernederlanderschappersoon"
                name="aanduidingbijzondernederlanderschappersoon"
                data-cy="aanduidingbijzondernederlanderschappersoon"
                type="text"
              />
              <ValidatedField
                label="Aanduidingnaamgebruik"
                id="natuurlijkpersoon-aanduidingnaamgebruik"
                name="aanduidingnaamgebruik"
                data-cy="aanduidingnaamgebruik"
                type="text"
              />
              <ValidatedField
                label="Aanhefaanschrijving"
                id="natuurlijkpersoon-aanhefaanschrijving"
                name="aanhefaanschrijving"
                data-cy="aanhefaanschrijving"
                type="text"
              />
              <ValidatedField
                label="Academischetitel"
                id="natuurlijkpersoon-academischetitel"
                name="academischetitel"
                data-cy="academischetitel"
                type="text"
              />
              <ValidatedField
                label="Achternaam"
                id="natuurlijkpersoon-achternaam"
                name="achternaam"
                data-cy="achternaam"
                type="text"
                validate={{
                  maxLength: { value: 100, message: 'This field cannot be longer than 100 characters.' },
                }}
              />
              <ValidatedField
                label="Adellijketitelofpredikaat"
                id="natuurlijkpersoon-adellijketitelofpredikaat"
                name="adellijketitelofpredikaat"
                data-cy="adellijketitelofpredikaat"
                type="text"
              />
              <ValidatedField
                label="Anummer"
                id="natuurlijkpersoon-anummer"
                name="anummer"
                data-cy="anummer"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Burgerservicenummer"
                id="natuurlijkpersoon-burgerservicenummer"
                name="burgerservicenummer"
                data-cy="burgerservicenummer"
                type="text"
              />
              <ValidatedField
                label="Datumgeboorte"
                id="natuurlijkpersoon-datumgeboorte"
                name="datumgeboorte"
                data-cy="datumgeboorte"
                type="text"
              />
              <ValidatedField
                label="Datumoverlijden"
                id="natuurlijkpersoon-datumoverlijden"
                name="datumoverlijden"
                data-cy="datumoverlijden"
                type="text"
              />
              <ValidatedField
                label="Geboorteland"
                id="natuurlijkpersoon-geboorteland"
                name="geboorteland"
                data-cy="geboorteland"
                type="text"
              />
              <ValidatedField
                label="Geboorteplaats"
                id="natuurlijkpersoon-geboorteplaats"
                name="geboorteplaats"
                data-cy="geboorteplaats"
                type="text"
              />
              <ValidatedField
                label="Geslachtsaanduiding"
                id="natuurlijkpersoon-geslachtsaanduiding"
                name="geslachtsaanduiding"
                data-cy="geslachtsaanduiding"
                type="text"
              />
              <ValidatedField
                label="Geslachtsnaam"
                id="natuurlijkpersoon-geslachtsnaam"
                name="geslachtsnaam"
                data-cy="geslachtsnaam"
                type="text"
              />
              <ValidatedField
                label="Geslachtsnaamaanschrijving"
                id="natuurlijkpersoon-geslachtsnaamaanschrijving"
                name="geslachtsnaamaanschrijving"
                data-cy="geslachtsnaamaanschrijving"
                type="text"
              />
              <ValidatedField
                label="Handlichting"
                id="natuurlijkpersoon-handlichting"
                name="handlichting"
                data-cy="handlichting"
                type="text"
              />
              <ValidatedField
                label="Indicatieafschermingpersoonsgegevens"
                id="natuurlijkpersoon-indicatieafschermingpersoonsgegevens"
                name="indicatieafschermingpersoonsgegevens"
                data-cy="indicatieafschermingpersoonsgegevens"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Indicatieoverleden"
                id="natuurlijkpersoon-indicatieoverleden"
                name="indicatieoverleden"
                data-cy="indicatieoverleden"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Landoverlijden"
                id="natuurlijkpersoon-landoverlijden"
                name="landoverlijden"
                data-cy="landoverlijden"
                type="text"
              />
              <ValidatedField
                label="Nationaliteit"
                id="natuurlijkpersoon-nationaliteit"
                name="nationaliteit"
                data-cy="nationaliteit"
                type="text"
                validate={{
                  maxLength: { value: 100, message: 'This field cannot be longer than 100 characters.' },
                }}
              />
              <ValidatedField
                label="Overlijdensplaats"
                id="natuurlijkpersoon-overlijdensplaats"
                name="overlijdensplaats"
                data-cy="overlijdensplaats"
                type="text"
              />
              <ValidatedField
                label="Voorlettersaanschrijving"
                id="natuurlijkpersoon-voorlettersaanschrijving"
                name="voorlettersaanschrijving"
                data-cy="voorlettersaanschrijving"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField label="Voornamen" id="natuurlijkpersoon-voornamen" name="voornamen" data-cy="voornamen" type="text" />
              <ValidatedField
                label="Voornamenaanschrijving"
                id="natuurlijkpersoon-voornamenaanschrijving"
                name="voornamenaanschrijving"
                data-cy="voornamenaanschrijving"
                type="text"
              />
              <ValidatedField
                label="Voorvoegselgeslachtsnaam"
                id="natuurlijkpersoon-voorvoegselgeslachtsnaam"
                name="voorvoegselgeslachtsnaam"
                data-cy="voorvoegselgeslachtsnaam"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/natuurlijkpersoon" replace color="info">
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

export default NatuurlijkpersoonUpdate;
