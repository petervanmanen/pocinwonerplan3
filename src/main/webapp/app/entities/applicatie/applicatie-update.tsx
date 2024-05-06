import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILeverancier } from 'app/shared/model/leverancier.model';
import { getEntities as getLeveranciers } from 'app/entities/leverancier/leverancier.reducer';
import { IDocument } from 'app/shared/model/document.model';
import { getEntities as getDocuments } from 'app/entities/document/document.reducer';
import { IMedewerker } from 'app/shared/model/medewerker.model';
import { getEntities as getMedewerkers } from 'app/entities/medewerker/medewerker.reducer';
import { IApplicatie } from 'app/shared/model/applicatie.model';
import { getEntity, updateEntity, createEntity, reset } from './applicatie.reducer';

export const ApplicatieUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const leveranciers = useAppSelector(state => state.leverancier.entities);
  const documents = useAppSelector(state => state.document.entities);
  const medewerkers = useAppSelector(state => state.medewerker.entities);
  const applicatieEntity = useAppSelector(state => state.applicatie.entity);
  const loading = useAppSelector(state => state.applicatie.loading);
  const updating = useAppSelector(state => state.applicatie.updating);
  const updateSuccess = useAppSelector(state => state.applicatie.updateSuccess);

  const handleClose = () => {
    navigate('/applicatie');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getLeveranciers({}));
    dispatch(getDocuments({}));
    dispatch(getMedewerkers({}));
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
      ...applicatieEntity,
      ...values,
      heeftleverancierLeverancier: leveranciers.find(it => it.id.toString() === values.heeftleverancierLeverancier?.toString()),
      heeftdocumentenDocuments: mapIdList(values.heeftdocumentenDocuments),
      rollenMedewerkers: mapIdList(values.rollenMedewerkers),
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
          ...applicatieEntity,
          heeftleverancierLeverancier: applicatieEntity?.heeftleverancierLeverancier?.id,
          heeftdocumentenDocuments: applicatieEntity?.heeftdocumentenDocuments?.map(e => e.id.toString()),
          rollenMedewerkers: applicatieEntity?.rollenMedewerkers?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.applicatie.home.createOrEditLabel" data-cy="ApplicatieCreateUpdateHeading">
            Create or edit a Applicatie
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="applicatie-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Applicatieurl"
                id="applicatie-applicatieurl"
                name="applicatieurl"
                data-cy="applicatieurl"
                type="text"
              />
              <ValidatedField label="Beheerstatus" id="applicatie-beheerstatus" name="beheerstatus" data-cy="beheerstatus" type="text" />
              <ValidatedField
                label="Beleidsdomein"
                id="applicatie-beleidsdomein"
                name="beleidsdomein"
                data-cy="beleidsdomein"
                type="text"
              />
              <ValidatedField label="Categorie" id="applicatie-categorie" name="categorie" data-cy="categorie" type="text" />
              <ValidatedField label="Guid" id="applicatie-guid" name="guid" data-cy="guid" type="text" />
              <ValidatedField label="Naam" id="applicatie-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField label="Omschrijving" id="applicatie-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField
                label="Packagingstatus"
                id="applicatie-packagingstatus"
                name="packagingstatus"
                data-cy="packagingstatus"
                type="text"
              />
              <ValidatedField
                id="applicatie-heeftleverancierLeverancier"
                name="heeftleverancierLeverancier"
                data-cy="heeftleverancierLeverancier"
                label="Heeftleverancier Leverancier"
                type="select"
              >
                <option value="" key="0" />
                {leveranciers
                  ? leveranciers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Heeftdocumenten Document"
                id="applicatie-heeftdocumentenDocument"
                data-cy="heeftdocumentenDocument"
                type="select"
                multiple
                name="heeftdocumentenDocuments"
              >
                <option value="" key="0" />
                {documents
                  ? documents.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Rollen Medewerker"
                id="applicatie-rollenMedewerker"
                data-cy="rollenMedewerker"
                type="select"
                multiple
                name="rollenMedewerkers"
              >
                <option value="" key="0" />
                {medewerkers
                  ? medewerkers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/applicatie" replace color="info">
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

export default ApplicatieUpdate;
