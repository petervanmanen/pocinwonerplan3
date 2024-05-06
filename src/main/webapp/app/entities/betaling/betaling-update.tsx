import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBankafschriftregel } from 'app/shared/model/bankafschriftregel.model';
import { getEntities as getBankafschriftregels } from 'app/entities/bankafschriftregel/bankafschriftregel.reducer';
import { IBankrekening } from 'app/shared/model/bankrekening.model';
import { getEntities as getBankrekenings } from 'app/entities/bankrekening/bankrekening.reducer';
import { IZaak } from 'app/shared/model/zaak.model';
import { getEntities as getZaaks } from 'app/entities/zaak/zaak.reducer';
import { IBetaling } from 'app/shared/model/betaling.model';
import { getEntity, updateEntity, createEntity, reset } from './betaling.reducer';

export const BetalingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const bankafschriftregels = useAppSelector(state => state.bankafschriftregel.entities);
  const bankrekenings = useAppSelector(state => state.bankrekening.entities);
  const zaaks = useAppSelector(state => state.zaak.entities);
  const betalingEntity = useAppSelector(state => state.betaling.entity);
  const loading = useAppSelector(state => state.betaling.loading);
  const updating = useAppSelector(state => state.betaling.updating);
  const updateSuccess = useAppSelector(state => state.betaling.updateSuccess);

  const handleClose = () => {
    navigate('/betaling');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getBankafschriftregels({}));
    dispatch(getBankrekenings({}));
    dispatch(getZaaks({}));
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
    if (values.bedrag !== undefined && typeof values.bedrag !== 'number') {
      values.bedrag = Number(values.bedrag);
    }

    const entity = {
      ...betalingEntity,
      ...values,
      komtvooropBankafschriftregel: bankafschriftregels.find(it => it.id.toString() === values.komtvooropBankafschriftregel?.toString()),
      vanBankrekening: bankrekenings.find(it => it.id.toString() === values.vanBankrekening?.toString()),
      naarBankrekening: bankrekenings.find(it => it.id.toString() === values.naarBankrekening?.toString()),
      heeftbetalingZaak: zaaks.find(it => it.id.toString() === values.heeftbetalingZaak?.toString()),
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
          ...betalingEntity,
          komtvooropBankafschriftregel: betalingEntity?.komtvooropBankafschriftregel?.id,
          vanBankrekening: betalingEntity?.vanBankrekening?.id,
          naarBankrekening: betalingEntity?.naarBankrekening?.id,
          heeftbetalingZaak: betalingEntity?.heeftbetalingZaak?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.betaling.home.createOrEditLabel" data-cy="BetalingCreateUpdateHeading">
            Create or edit a Betaling
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="betaling-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Bedrag" id="betaling-bedrag" name="bedrag" data-cy="bedrag" type="text" />
              <ValidatedField label="Datumtijd" id="betaling-datumtijd" name="datumtijd" data-cy="datumtijd" type="text" />
              <ValidatedField label="Omschrijving" id="betaling-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField label="Valuta" id="betaling-valuta" name="valuta" data-cy="valuta" type="text" />
              <ValidatedField
                id="betaling-komtvooropBankafschriftregel"
                name="komtvooropBankafschriftregel"
                data-cy="komtvooropBankafschriftregel"
                label="Komtvoorop Bankafschriftregel"
                type="select"
              >
                <option value="" key="0" />
                {bankafschriftregels
                  ? bankafschriftregels.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="betaling-vanBankrekening"
                name="vanBankrekening"
                data-cy="vanBankrekening"
                label="Van Bankrekening"
                type="select"
              >
                <option value="" key="0" />
                {bankrekenings
                  ? bankrekenings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="betaling-naarBankrekening"
                name="naarBankrekening"
                data-cy="naarBankrekening"
                label="Naar Bankrekening"
                type="select"
              >
                <option value="" key="0" />
                {bankrekenings
                  ? bankrekenings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="betaling-heeftbetalingZaak"
                name="heeftbetalingZaak"
                data-cy="heeftbetalingZaak"
                label="Heeftbetaling Zaak"
                type="select"
              >
                <option value="" key="0" />
                {zaaks
                  ? zaaks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/betaling" replace color="info">
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

export default BetalingUpdate;
