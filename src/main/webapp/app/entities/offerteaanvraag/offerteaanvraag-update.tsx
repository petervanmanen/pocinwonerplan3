import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAanbesteding } from 'app/shared/model/aanbesteding.model';
import { getEntities as getAanbestedings } from 'app/entities/aanbesteding/aanbesteding.reducer';
import { ILeverancier } from 'app/shared/model/leverancier.model';
import { getEntities as getLeveranciers } from 'app/entities/leverancier/leverancier.reducer';
import { IOfferteaanvraag } from 'app/shared/model/offerteaanvraag.model';
import { getEntity, updateEntity, createEntity, reset } from './offerteaanvraag.reducer';

export const OfferteaanvraagUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const aanbestedings = useAppSelector(state => state.aanbesteding.entities);
  const leveranciers = useAppSelector(state => state.leverancier.entities);
  const offerteaanvraagEntity = useAppSelector(state => state.offerteaanvraag.entity);
  const loading = useAppSelector(state => state.offerteaanvraag.loading);
  const updating = useAppSelector(state => state.offerteaanvraag.updating);
  const updateSuccess = useAppSelector(state => state.offerteaanvraag.updateSuccess);

  const handleClose = () => {
    navigate('/offerteaanvraag');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getAanbestedings({}));
    dispatch(getLeveranciers({}));
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
      ...offerteaanvraagEntity,
      ...values,
      betreftAanbesteding: aanbestedings.find(it => it.id.toString() === values.betreftAanbesteding?.toString()),
      gerichtaanLeverancier: leveranciers.find(it => it.id.toString() === values.gerichtaanLeverancier?.toString()),
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
          ...offerteaanvraagEntity,
          betreftAanbesteding: offerteaanvraagEntity?.betreftAanbesteding?.id,
          gerichtaanLeverancier: offerteaanvraagEntity?.gerichtaanLeverancier?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.offerteaanvraag.home.createOrEditLabel" data-cy="OfferteaanvraagCreateUpdateHeading">
            Create or edit a Offerteaanvraag
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
                <ValidatedField name="id" required readOnly id="offerteaanvraag-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Datumaanvraag"
                id="offerteaanvraag-datumaanvraag"
                name="datumaanvraag"
                data-cy="datumaanvraag"
                type="date"
              />
              <ValidatedField
                label="Datumsluiting"
                id="offerteaanvraag-datumsluiting"
                name="datumsluiting"
                data-cy="datumsluiting"
                type="text"
              />
              <ValidatedField label="Naam" id="offerteaanvraag-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField
                label="Omschrijving"
                id="offerteaanvraag-omschrijving"
                name="omschrijving"
                data-cy="omschrijving"
                type="text"
              />
              <ValidatedField
                id="offerteaanvraag-betreftAanbesteding"
                name="betreftAanbesteding"
                data-cy="betreftAanbesteding"
                label="Betreft Aanbesteding"
                type="select"
              >
                <option value="" key="0" />
                {aanbestedings
                  ? aanbestedings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="offerteaanvraag-gerichtaanLeverancier"
                name="gerichtaanLeverancier"
                data-cy="gerichtaanLeverancier"
                label="Gerichtaan Leverancier"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/offerteaanvraag" replace color="info">
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

export default OfferteaanvraagUpdate;
