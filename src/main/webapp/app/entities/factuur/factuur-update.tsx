import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IKostenplaats } from 'app/shared/model/kostenplaats.model';
import { getEntities as getKostenplaats } from 'app/entities/kostenplaats/kostenplaats.reducer';
import { IInkooporder } from 'app/shared/model/inkooporder.model';
import { getEntities as getInkooporders } from 'app/entities/inkooporder/inkooporder.reducer';
import { ILeverancier } from 'app/shared/model/leverancier.model';
import { getEntities as getLeveranciers } from 'app/entities/leverancier/leverancier.reducer';
import { IDebiteur } from 'app/shared/model/debiteur.model';
import { getEntities as getDebiteurs } from 'app/entities/debiteur/debiteur.reducer';
import { IFactuur } from 'app/shared/model/factuur.model';
import { getEntity, updateEntity, createEntity, reset } from './factuur.reducer';

export const FactuurUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const kostenplaats = useAppSelector(state => state.kostenplaats.entities);
  const inkooporders = useAppSelector(state => state.inkooporder.entities);
  const leveranciers = useAppSelector(state => state.leverancier.entities);
  const debiteurs = useAppSelector(state => state.debiteur.entities);
  const factuurEntity = useAppSelector(state => state.factuur.entity);
  const loading = useAppSelector(state => state.factuur.loading);
  const updating = useAppSelector(state => state.factuur.updating);
  const updateSuccess = useAppSelector(state => state.factuur.updateSuccess);

  const handleClose = () => {
    navigate('/factuur');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getKostenplaats({}));
    dispatch(getInkooporders({}));
    dispatch(getLeveranciers({}));
    dispatch(getDebiteurs({}));
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
    if (values.factuurbedragbtw !== undefined && typeof values.factuurbedragbtw !== 'number') {
      values.factuurbedragbtw = Number(values.factuurbedragbtw);
    }

    const entity = {
      ...factuurEntity,
      ...values,
      schrijftopKostenplaats: kostenplaats.find(it => it.id.toString() === values.schrijftopKostenplaats?.toString()),
      gedektviaInkooporder: inkooporders.find(it => it.id.toString() === values.gedektviaInkooporder?.toString()),
      crediteurLeverancier: leveranciers.find(it => it.id.toString() === values.crediteurLeverancier?.toString()),
      heeftDebiteur: debiteurs.find(it => it.id.toString() === values.heeftDebiteur?.toString()),
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
          ...factuurEntity,
          schrijftopKostenplaats: factuurEntity?.schrijftopKostenplaats?.id,
          gedektviaInkooporder: factuurEntity?.gedektviaInkooporder?.id,
          crediteurLeverancier: factuurEntity?.crediteurLeverancier?.id,
          heeftDebiteur: factuurEntity?.heeftDebiteur?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.factuur.home.createOrEditLabel" data-cy="FactuurCreateUpdateHeading">
            Create or edit a Factuur
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="factuur-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Betaalbaarper" id="factuur-betaalbaarper" name="betaalbaarper" data-cy="betaalbaarper" type="text" />
              <ValidatedField label="Betaaltermijn" id="factuur-betaaltermijn" name="betaaltermijn" data-cy="betaaltermijn" type="text" />
              <ValidatedField label="Code" id="factuur-code" name="code" data-cy="code" type="text" />
              <ValidatedField label="Datumfactuur" id="factuur-datumfactuur" name="datumfactuur" data-cy="datumfactuur" type="text" />
              <ValidatedField
                label="Factuurbedragbtw"
                id="factuur-factuurbedragbtw"
                name="factuurbedragbtw"
                data-cy="factuurbedragbtw"
                type="text"
              />
              <ValidatedField
                label="Factuurbedragexclusiefbtw"
                id="factuur-factuurbedragexclusiefbtw"
                name="factuurbedragexclusiefbtw"
                data-cy="factuurbedragexclusiefbtw"
                type="text"
              />
              <ValidatedField label="Omschrijving" id="factuur-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField
                id="factuur-schrijftopKostenplaats"
                name="schrijftopKostenplaats"
                data-cy="schrijftopKostenplaats"
                label="Schrijftop Kostenplaats"
                type="select"
              >
                <option value="" key="0" />
                {kostenplaats
                  ? kostenplaats.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="factuur-gedektviaInkooporder"
                name="gedektviaInkooporder"
                data-cy="gedektviaInkooporder"
                label="Gedektvia Inkooporder"
                type="select"
              >
                <option value="" key="0" />
                {inkooporders
                  ? inkooporders.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="factuur-crediteurLeverancier"
                name="crediteurLeverancier"
                data-cy="crediteurLeverancier"
                label="Crediteur Leverancier"
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
              <ValidatedField id="factuur-heeftDebiteur" name="heeftDebiteur" data-cy="heeftDebiteur" label="Heeft Debiteur" type="select">
                <option value="" key="0" />
                {debiteurs
                  ? debiteurs.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/factuur" replace color="info">
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

export default FactuurUpdate;
