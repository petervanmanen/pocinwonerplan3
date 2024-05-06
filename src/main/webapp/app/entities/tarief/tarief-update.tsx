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
import { IContract } from 'app/shared/model/contract.model';
import { getEntities as getContracts } from 'app/entities/contract/contract.reducer';
import { IVoorziening } from 'app/shared/model/voorziening.model';
import { getEntities as getVoorzienings } from 'app/entities/voorziening/voorziening.reducer';
import { ITarief } from 'app/shared/model/tarief.model';
import { getEntity, updateEntity, createEntity, reset } from './tarief.reducer';

export const TariefUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const leveranciers = useAppSelector(state => state.leverancier.entities);
  const contracts = useAppSelector(state => state.contract.entities);
  const voorzienings = useAppSelector(state => state.voorziening.entities);
  const tariefEntity = useAppSelector(state => state.tarief.entity);
  const loading = useAppSelector(state => state.tarief.loading);
  const updating = useAppSelector(state => state.tarief.updating);
  const updateSuccess = useAppSelector(state => state.tarief.updateSuccess);

  const handleClose = () => {
    navigate('/tarief');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getLeveranciers({}));
    dispatch(getContracts({}));
    dispatch(getVoorzienings({}));
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
      ...tariefEntity,
      ...values,
      heeftLeverancier: leveranciers.find(it => it.id.toString() === values.heeftLeverancier?.toString()),
      bevatContract: contracts.find(it => it.id.toString() === values.bevatContract?.toString()),
      heeftVoorziening: voorzienings.find(it => it.id.toString() === values.heeftVoorziening?.toString()),
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
          ...tariefEntity,
          heeftLeverancier: tariefEntity?.heeftLeverancier?.id,
          bevatContract: tariefEntity?.bevatContract?.id,
          heeftVoorziening: tariefEntity?.heeftVoorziening?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.tarief.home.createOrEditLabel" data-cy="TariefCreateUpdateHeading">
            Create or edit a Tarief
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="tarief-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Bedrag" id="tarief-bedrag" name="bedrag" data-cy="bedrag" type="text" />
              <ValidatedField label="Datumeinde" id="tarief-datumeinde" name="datumeinde" data-cy="datumeinde" type="date" />
              <ValidatedField label="Datumstart" id="tarief-datumstart" name="datumstart" data-cy="datumstart" type="date" />
              <ValidatedField label="Eenheid" id="tarief-eenheid" name="eenheid" data-cy="eenheid" type="text" />
              <ValidatedField label="Wet" id="tarief-wet" name="wet" data-cy="wet" type="text" />
              <ValidatedField
                id="tarief-heeftLeverancier"
                name="heeftLeverancier"
                data-cy="heeftLeverancier"
                label="Heeft Leverancier"
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
              <ValidatedField id="tarief-bevatContract" name="bevatContract" data-cy="bevatContract" label="Bevat Contract" type="select">
                <option value="" key="0" />
                {contracts
                  ? contracts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="tarief-heeftVoorziening"
                name="heeftVoorziening"
                data-cy="heeftVoorziening"
                label="Heeft Voorziening"
                type="select"
                required
              >
                <option value="" key="0" />
                {voorzienings
                  ? voorzienings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/tarief" replace color="info">
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

export default TariefUpdate;
