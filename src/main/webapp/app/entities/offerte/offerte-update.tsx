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
import { IOfferte } from 'app/shared/model/offerte.model';
import { getEntity, updateEntity, createEntity, reset } from './offerte.reducer';

export const OfferteUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const aanbestedings = useAppSelector(state => state.aanbesteding.entities);
  const leveranciers = useAppSelector(state => state.leverancier.entities);
  const offerteEntity = useAppSelector(state => state.offerte.entity);
  const loading = useAppSelector(state => state.offerte.loading);
  const updating = useAppSelector(state => state.offerte.updating);
  const updateSuccess = useAppSelector(state => state.offerte.updateSuccess);

  const handleClose = () => {
    navigate('/offerte');
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
      ...offerteEntity,
      ...values,
      betreftAanbesteding: aanbestedings.find(it => it.id.toString() === values.betreftAanbesteding?.toString()),
      ingedienddoorLeverancier: leveranciers.find(it => it.id.toString() === values.ingedienddoorLeverancier?.toString()),
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
          ...offerteEntity,
          betreftAanbesteding: offerteEntity?.betreftAanbesteding?.id,
          ingedienddoorLeverancier: offerteEntity?.ingedienddoorLeverancier?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.offerte.home.createOrEditLabel" data-cy="OfferteCreateUpdateHeading">
            Create or edit a Offerte
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="offerte-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                id="offerte-betreftAanbesteding"
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
                id="offerte-ingedienddoorLeverancier"
                name="ingedienddoorLeverancier"
                data-cy="ingedienddoorLeverancier"
                label="Ingedienddoor Leverancier"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/offerte" replace color="info">
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

export default OfferteUpdate;
