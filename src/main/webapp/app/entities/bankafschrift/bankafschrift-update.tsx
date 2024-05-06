import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBankrekening } from 'app/shared/model/bankrekening.model';
import { getEntities as getBankrekenings } from 'app/entities/bankrekening/bankrekening.reducer';
import { IBankafschrift } from 'app/shared/model/bankafschrift.model';
import { getEntity, updateEntity, createEntity, reset } from './bankafschrift.reducer';

export const BankafschriftUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const bankrekenings = useAppSelector(state => state.bankrekening.entities);
  const bankafschriftEntity = useAppSelector(state => state.bankafschrift.entity);
  const loading = useAppSelector(state => state.bankafschrift.loading);
  const updating = useAppSelector(state => state.bankafschrift.updating);
  const updateSuccess = useAppSelector(state => state.bankafschrift.updateSuccess);

  const handleClose = () => {
    navigate('/bankafschrift');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getBankrekenings({}));
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
      ...bankafschriftEntity,
      ...values,
      heeftBankrekening: bankrekenings.find(it => it.id.toString() === values.heeftBankrekening?.toString()),
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
          ...bankafschriftEntity,
          heeftBankrekening: bankafschriftEntity?.heeftBankrekening?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.bankafschrift.home.createOrEditLabel" data-cy="BankafschriftCreateUpdateHeading">
            Create or edit a Bankafschrift
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
                <ValidatedField name="id" required readOnly id="bankafschrift-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Datum" id="bankafschrift-datum" name="datum" data-cy="datum" type="text" />
              <ValidatedField label="Nummer" id="bankafschrift-nummer" name="nummer" data-cy="nummer" type="text" />
              <ValidatedField
                id="bankafschrift-heeftBankrekening"
                name="heeftBankrekening"
                data-cy="heeftBankrekening"
                label="Heeft Bankrekening"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/bankafschrift" replace color="info">
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

export default BankafschriftUpdate;
