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
import { IDeclaratiesoort } from 'app/shared/model/declaratiesoort.model';
import { getEntities as getDeclaratiesoorts } from 'app/entities/declaratiesoort/declaratiesoort.reducer';
import { IWerknemer } from 'app/shared/model/werknemer.model';
import { getEntities as getWerknemers } from 'app/entities/werknemer/werknemer.reducer';
import { IDeclaratie } from 'app/shared/model/declaratie.model';
import { getEntity, updateEntity, createEntity, reset } from './declaratie.reducer';

export const DeclaratieUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const leveranciers = useAppSelector(state => state.leverancier.entities);
  const declaratiesoorts = useAppSelector(state => state.declaratiesoort.entities);
  const werknemers = useAppSelector(state => state.werknemer.entities);
  const declaratieEntity = useAppSelector(state => state.declaratie.entity);
  const loading = useAppSelector(state => state.declaratie.loading);
  const updating = useAppSelector(state => state.declaratie.updating);
  const updateSuccess = useAppSelector(state => state.declaratie.updateSuccess);

  const handleClose = () => {
    navigate('/declaratie');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getLeveranciers({}));
    dispatch(getDeclaratiesoorts({}));
    dispatch(getWerknemers({}));
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
      ...declaratieEntity,
      ...values,
      ingedienddoorLeverancier: leveranciers.find(it => it.id.toString() === values.ingedienddoorLeverancier?.toString()),
      soortdeclaratieDeclaratiesoort: declaratiesoorts.find(it => it.id.toString() === values.soortdeclaratieDeclaratiesoort?.toString()),
      dientinWerknemer: werknemers.find(it => it.id.toString() === values.dientinWerknemer?.toString()),
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
          ...declaratieEntity,
          ingedienddoorLeverancier: declaratieEntity?.ingedienddoorLeverancier?.id,
          soortdeclaratieDeclaratiesoort: declaratieEntity?.soortdeclaratieDeclaratiesoort?.id,
          dientinWerknemer: declaratieEntity?.dientinWerknemer?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.declaratie.home.createOrEditLabel" data-cy="DeclaratieCreateUpdateHeading">
            Create or edit a Declaratie
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="declaratie-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Datumdeclaratie"
                id="declaratie-datumdeclaratie"
                name="datumdeclaratie"
                data-cy="datumdeclaratie"
                type="text"
              />
              <ValidatedField
                label="Declaratiebedrag"
                id="declaratie-declaratiebedrag"
                name="declaratiebedrag"
                data-cy="declaratiebedrag"
                type="text"
              />
              <ValidatedField
                label="Declaratiestatus"
                id="declaratie-declaratiestatus"
                name="declaratiestatus"
                data-cy="declaratiestatus"
                type="text"
              />
              <ValidatedField
                id="declaratie-ingedienddoorLeverancier"
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
              <ValidatedField
                id="declaratie-soortdeclaratieDeclaratiesoort"
                name="soortdeclaratieDeclaratiesoort"
                data-cy="soortdeclaratieDeclaratiesoort"
                label="Soortdeclaratie Declaratiesoort"
                type="select"
              >
                <option value="" key="0" />
                {declaratiesoorts
                  ? declaratiesoorts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="declaratie-dientinWerknemer"
                name="dientinWerknemer"
                data-cy="dientinWerknemer"
                label="Dientin Werknemer"
                type="select"
              >
                <option value="" key="0" />
                {werknemers
                  ? werknemers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/declaratie" replace color="info">
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

export default DeclaratieUpdate;
