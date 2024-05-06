import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getContracts } from 'app/entities/contract/contract.reducer';
import { ILeverancier } from 'app/shared/model/leverancier.model';
import { getEntities as getLeveranciers } from 'app/entities/leverancier/leverancier.reducer';
import { IContract } from 'app/shared/model/contract.model';
import { getEntity, updateEntity, createEntity, reset } from './contract.reducer';

export const ContractUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const contracts = useAppSelector(state => state.contract.entities);
  const leveranciers = useAppSelector(state => state.leverancier.entities);
  const contractEntity = useAppSelector(state => state.contract.entity);
  const loading = useAppSelector(state => state.contract.loading);
  const updating = useAppSelector(state => state.contract.updating);
  const updateSuccess = useAppSelector(state => state.contract.updateSuccess);

  const handleClose = () => {
    navigate('/contract');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getContracts({}));
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
      ...contractEntity,
      ...values,
      bovenliggendContract: contracts.find(it => it.id.toString() === values.bovenliggendContract?.toString()),
      heeftLeverancier: leveranciers.find(it => it.id.toString() === values.heeftLeverancier?.toString()),
      contractantLeverancier: leveranciers.find(it => it.id.toString() === values.contractantLeverancier?.toString()),
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
          ...contractEntity,
          bovenliggendContract: contractEntity?.bovenliggendContract?.id,
          heeftLeverancier: contractEntity?.heeftLeverancier?.id,
          contractantLeverancier: contractEntity?.contractantLeverancier?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.contract.home.createOrEditLabel" data-cy="ContractCreateUpdateHeading">
            Create or edit a Contract
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="contract-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Autorisatiegroep"
                id="contract-autorisatiegroep"
                name="autorisatiegroep"
                data-cy="autorisatiegroep"
                type="text"
              />
              <ValidatedField label="Beschrijving" id="contract-beschrijving" name="beschrijving" data-cy="beschrijving" type="text" />
              <ValidatedField label="Categorie" id="contract-categorie" name="categorie" data-cy="categorie" type="text" />
              <ValidatedField label="Classificatie" id="contract-classificatie" name="classificatie" data-cy="classificatie" type="text" />
              <ValidatedField
                label="Contractrevisie"
                id="contract-contractrevisie"
                name="contractrevisie"
                data-cy="contractrevisie"
                type="text"
              />
              <ValidatedField label="Datumcreatie" id="contract-datumcreatie" name="datumcreatie" data-cy="datumcreatie" type="date" />
              <ValidatedField label="Datumeinde" id="contract-datumeinde" name="datumeinde" data-cy="datumeinde" type="date" />
              <ValidatedField label="Datumstart" id="contract-datumstart" name="datumstart" data-cy="datumstart" type="date" />
              <ValidatedField label="Groep" id="contract-groep" name="groep" data-cy="groep" type="text" />
              <ValidatedField
                label="Interncontractid"
                id="contract-interncontractid"
                name="interncontractid"
                data-cy="interncontractid"
                type="text"
              />
              <ValidatedField
                label="Interncontractrevisie"
                id="contract-interncontractrevisie"
                name="interncontractrevisie"
                data-cy="interncontractrevisie"
                type="text"
              />
              <ValidatedField label="Opmerkingen" id="contract-opmerkingen" name="opmerkingen" data-cy="opmerkingen" type="text" />
              <ValidatedField label="Status" id="contract-status" name="status" data-cy="status" type="text" />
              <ValidatedField label="Type" id="contract-type" name="type" data-cy="type" type="text" />
              <ValidatedField label="Voorwaarde" id="contract-voorwaarde" name="voorwaarde" data-cy="voorwaarde" type="text" />
              <ValidatedField label="Zoekwoorden" id="contract-zoekwoorden" name="zoekwoorden" data-cy="zoekwoorden" type="text" />
              <ValidatedField
                id="contract-bovenliggendContract"
                name="bovenliggendContract"
                data-cy="bovenliggendContract"
                label="Bovenliggend Contract"
                type="select"
              >
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
                id="contract-heeftLeverancier"
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
              <ValidatedField
                id="contract-contractantLeverancier"
                name="contractantLeverancier"
                data-cy="contractantLeverancier"
                label="Contractant Leverancier"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/contract" replace color="info">
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

export default ContractUpdate;
