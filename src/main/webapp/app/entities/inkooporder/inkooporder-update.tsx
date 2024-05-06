import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IContract } from 'app/shared/model/contract.model';
import { getEntities as getContracts } from 'app/entities/contract/contract.reducer';
import { getEntities as getInkooporders } from 'app/entities/inkooporder/inkooporder.reducer';
import { IInkooppakket } from 'app/shared/model/inkooppakket.model';
import { getEntities as getInkooppakkets } from 'app/entities/inkooppakket/inkooppakket.reducer';
import { ILeverancier } from 'app/shared/model/leverancier.model';
import { getEntities as getLeveranciers } from 'app/entities/leverancier/leverancier.reducer';
import { IHoofdrekening } from 'app/shared/model/hoofdrekening.model';
import { getEntities as getHoofdrekenings } from 'app/entities/hoofdrekening/hoofdrekening.reducer';
import { IKostenplaats } from 'app/shared/model/kostenplaats.model';
import { getEntities as getKostenplaats } from 'app/entities/kostenplaats/kostenplaats.reducer';
import { IInkooporder } from 'app/shared/model/inkooporder.model';
import { getEntity, updateEntity, createEntity, reset } from './inkooporder.reducer';

export const InkooporderUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const contracts = useAppSelector(state => state.contract.entities);
  const inkooporders = useAppSelector(state => state.inkooporder.entities);
  const inkooppakkets = useAppSelector(state => state.inkooppakket.entities);
  const leveranciers = useAppSelector(state => state.leverancier.entities);
  const hoofdrekenings = useAppSelector(state => state.hoofdrekening.entities);
  const kostenplaats = useAppSelector(state => state.kostenplaats.entities);
  const inkooporderEntity = useAppSelector(state => state.inkooporder.entity);
  const loading = useAppSelector(state => state.inkooporder.loading);
  const updating = useAppSelector(state => state.inkooporder.updating);
  const updateSuccess = useAppSelector(state => state.inkooporder.updateSuccess);

  const handleClose = () => {
    navigate('/inkooporder');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getContracts({}));
    dispatch(getInkooporders({}));
    dispatch(getInkooppakkets({}));
    dispatch(getLeveranciers({}));
    dispatch(getHoofdrekenings({}));
    dispatch(getKostenplaats({}));
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
    if (values.saldo !== undefined && typeof values.saldo !== 'number') {
      values.saldo = Number(values.saldo);
    }
    if (values.totaalnettobedrag !== undefined && typeof values.totaalnettobedrag !== 'number') {
      values.totaalnettobedrag = Number(values.totaalnettobedrag);
    }

    const entity = {
      ...inkooporderEntity,
      ...values,
      betreftContract: contracts.find(it => it.id.toString() === values.betreftContract?.toString()),
      oorspronkelijkInkooporder: inkooporders.find(it => it.id.toString() === values.oorspronkelijkInkooporder?.toString()),
      heeftInkooppakket: inkooppakkets.find(it => it.id.toString() === values.heeftInkooppakket?.toString()),
      verplichtingaanLeverancier: leveranciers.find(it => it.id.toString() === values.verplichtingaanLeverancier?.toString()),
      wordtgeschrevenopHoofdrekenings: mapIdList(values.wordtgeschrevenopHoofdrekenings),
      gerelateerdInkooporder2: inkooporders.find(it => it.id.toString() === values.gerelateerdInkooporder2?.toString()),
      heeftKostenplaats: mapIdList(values.heeftKostenplaats),
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
          ...inkooporderEntity,
          betreftContract: inkooporderEntity?.betreftContract?.id,
          oorspronkelijkInkooporder: inkooporderEntity?.oorspronkelijkInkooporder?.id,
          heeftInkooppakket: inkooporderEntity?.heeftInkooppakket?.id,
          verplichtingaanLeverancier: inkooporderEntity?.verplichtingaanLeverancier?.id,
          wordtgeschrevenopHoofdrekenings: inkooporderEntity?.wordtgeschrevenopHoofdrekenings?.map(e => e.id.toString()),
          gerelateerdInkooporder2: inkooporderEntity?.gerelateerdInkooporder2?.id,
          heeftKostenplaats: inkooporderEntity?.heeftKostenplaats?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.inkooporder.home.createOrEditLabel" data-cy="InkooporderCreateUpdateHeading">
            Create or edit a Inkooporder
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="inkooporder-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Artikelcode" id="inkooporder-artikelcode" name="artikelcode" data-cy="artikelcode" type="text" />
              <ValidatedField
                label="Betalingmeerderejaren"
                id="inkooporder-betalingmeerderejaren"
                name="betalingmeerderejaren"
                data-cy="betalingmeerderejaren"
                check
                type="checkbox"
              />
              <ValidatedField label="Betreft" id="inkooporder-betreft" name="betreft" data-cy="betreft" type="text" />
              <ValidatedField label="Datumeinde" id="inkooporder-datumeinde" name="datumeinde" data-cy="datumeinde" type="text" />
              <ValidatedField
                label="Datumingediend"
                id="inkooporder-datumingediend"
                name="datumingediend"
                data-cy="datumingediend"
                type="text"
              />
              <ValidatedField label="Datumstart" id="inkooporder-datumstart" name="datumstart" data-cy="datumstart" type="text" />
              <ValidatedField label="Goederencode" id="inkooporder-goederencode" name="goederencode" data-cy="goederencode" type="text" />
              <ValidatedField label="Omschrijving" id="inkooporder-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField
                label="Ordernummer"
                id="inkooporder-ordernummer"
                name="ordernummer"
                data-cy="ordernummer"
                type="text"
                validate={{
                  maxLength: { value: 8, message: 'This field cannot be longer than 8 characters.' },
                }}
              />
              <ValidatedField label="Saldo" id="inkooporder-saldo" name="saldo" data-cy="saldo" type="text" />
              <ValidatedField
                label="Totaalnettobedrag"
                id="inkooporder-totaalnettobedrag"
                name="totaalnettobedrag"
                data-cy="totaalnettobedrag"
                type="text"
              />
              <ValidatedField
                label="Wijzevanaanbesteden"
                id="inkooporder-wijzevanaanbesteden"
                name="wijzevanaanbesteden"
                data-cy="wijzevanaanbesteden"
                type="text"
              />
              <ValidatedField
                id="inkooporder-betreftContract"
                name="betreftContract"
                data-cy="betreftContract"
                label="Betreft Contract"
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
                id="inkooporder-oorspronkelijkInkooporder"
                name="oorspronkelijkInkooporder"
                data-cy="oorspronkelijkInkooporder"
                label="Oorspronkelijk Inkooporder"
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
                id="inkooporder-heeftInkooppakket"
                name="heeftInkooppakket"
                data-cy="heeftInkooppakket"
                label="Heeft Inkooppakket"
                type="select"
              >
                <option value="" key="0" />
                {inkooppakkets
                  ? inkooppakkets.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="inkooporder-verplichtingaanLeverancier"
                name="verplichtingaanLeverancier"
                data-cy="verplichtingaanLeverancier"
                label="Verplichtingaan Leverancier"
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
                label="Wordtgeschrevenop Hoofdrekening"
                id="inkooporder-wordtgeschrevenopHoofdrekening"
                data-cy="wordtgeschrevenopHoofdrekening"
                type="select"
                multiple
                name="wordtgeschrevenopHoofdrekenings"
              >
                <option value="" key="0" />
                {hoofdrekenings
                  ? hoofdrekenings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="inkooporder-gerelateerdInkooporder2"
                name="gerelateerdInkooporder2"
                data-cy="gerelateerdInkooporder2"
                label="Gerelateerd Inkooporder 2"
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
                label="Heeft Kostenplaats"
                id="inkooporder-heeftKostenplaats"
                data-cy="heeftKostenplaats"
                type="select"
                multiple
                name="heeftKostenplaats"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/inkooporder" replace color="info">
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

export default InkooporderUpdate;
