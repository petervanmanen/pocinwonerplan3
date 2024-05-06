import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMutatie } from 'app/shared/model/mutatie.model';
import { getEntities as getMutaties } from 'app/entities/mutatie/mutatie.reducer';
import { IBankafschrift } from 'app/shared/model/bankafschrift.model';
import { getEntities as getBankafschrifts } from 'app/entities/bankafschrift/bankafschrift.reducer';
import { IBankafschriftregel } from 'app/shared/model/bankafschriftregel.model';
import { getEntity, updateEntity, createEntity, reset } from './bankafschriftregel.reducer';

export const BankafschriftregelUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const mutaties = useAppSelector(state => state.mutatie.entities);
  const bankafschrifts = useAppSelector(state => state.bankafschrift.entities);
  const bankafschriftregelEntity = useAppSelector(state => state.bankafschriftregel.entity);
  const loading = useAppSelector(state => state.bankafschriftregel.loading);
  const updating = useAppSelector(state => state.bankafschriftregel.updating);
  const updateSuccess = useAppSelector(state => state.bankafschriftregel.updateSuccess);

  const handleClose = () => {
    navigate('/bankafschriftregel');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getMutaties({}));
    dispatch(getBankafschrifts({}));
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
      ...bankafschriftregelEntity,
      ...values,
      leidttotMutatie: mutaties.find(it => it.id.toString() === values.leidttotMutatie?.toString()),
      heeftBankafschrift: bankafschrifts.find(it => it.id.toString() === values.heeftBankafschrift?.toString()),
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
          ...bankafschriftregelEntity,
          leidttotMutatie: bankafschriftregelEntity?.leidttotMutatie?.id,
          heeftBankafschrift: bankafschriftregelEntity?.heeftBankafschrift?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.bankafschriftregel.home.createOrEditLabel" data-cy="BankafschriftregelCreateUpdateHeading">
            Create or edit a Bankafschriftregel
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
                <ValidatedField name="id" required readOnly id="bankafschriftregel-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Bedrag" id="bankafschriftregel-bedrag" name="bedrag" data-cy="bedrag" type="text" />
              <ValidatedField label="Bij" id="bankafschriftregel-bij" name="bij" data-cy="bij" check type="checkbox" />
              <ValidatedField label="Datum" id="bankafschriftregel-datum" name="datum" data-cy="datum" type="text" />
              <ValidatedField
                label="Rekeningvan"
                id="bankafschriftregel-rekeningvan"
                name="rekeningvan"
                data-cy="rekeningvan"
                type="text"
              />
              <ValidatedField
                id="bankafschriftregel-leidttotMutatie"
                name="leidttotMutatie"
                data-cy="leidttotMutatie"
                label="Leidttot Mutatie"
                type="select"
              >
                <option value="" key="0" />
                {mutaties
                  ? mutaties.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="bankafschriftregel-heeftBankafschrift"
                name="heeftBankafschrift"
                data-cy="heeftBankafschrift"
                label="Heeft Bankafschrift"
                type="select"
              >
                <option value="" key="0" />
                {bankafschrifts
                  ? bankafschrifts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/bankafschriftregel" replace color="info">
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

export default BankafschriftregelUpdate;
