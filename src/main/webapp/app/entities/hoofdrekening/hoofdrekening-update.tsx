import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IActiva } from 'app/shared/model/activa.model';
import { getEntities as getActivas } from 'app/entities/activa/activa.reducer';
import { IKostenplaats } from 'app/shared/model/kostenplaats.model';
import { getEntities as getKostenplaats } from 'app/entities/kostenplaats/kostenplaats.reducer';
import { getEntities as getHoofdrekenings } from 'app/entities/hoofdrekening/hoofdrekening.reducer';
import { IInkooporder } from 'app/shared/model/inkooporder.model';
import { getEntities as getInkooporders } from 'app/entities/inkooporder/inkooporder.reducer';
import { IHoofdrekening } from 'app/shared/model/hoofdrekening.model';
import { getEntity, updateEntity, createEntity, reset } from './hoofdrekening.reducer';

export const HoofdrekeningUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const activas = useAppSelector(state => state.activa.entities);
  const kostenplaats = useAppSelector(state => state.kostenplaats.entities);
  const hoofdrekenings = useAppSelector(state => state.hoofdrekening.entities);
  const inkooporders = useAppSelector(state => state.inkooporder.entities);
  const hoofdrekeningEntity = useAppSelector(state => state.hoofdrekening.entity);
  const loading = useAppSelector(state => state.hoofdrekening.loading);
  const updating = useAppSelector(state => state.hoofdrekening.updating);
  const updateSuccess = useAppSelector(state => state.hoofdrekening.updateSuccess);

  const handleClose = () => {
    navigate('/hoofdrekening');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getActivas({}));
    dispatch(getKostenplaats({}));
    dispatch(getHoofdrekenings({}));
    dispatch(getInkooporders({}));
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
      ...hoofdrekeningEntity,
      ...values,
      heeftActivas: mapIdList(values.heeftActivas),
      heeftKostenplaats: mapIdList(values.heeftKostenplaats),
      valtbinnenHoofdrekening2: hoofdrekenings.find(it => it.id.toString() === values.valtbinnenHoofdrekening2?.toString()),
      wordtgeschrevenopInkooporders: mapIdList(values.wordtgeschrevenopInkooporders),
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
          ...hoofdrekeningEntity,
          heeftActivas: hoofdrekeningEntity?.heeftActivas?.map(e => e.id.toString()),
          heeftKostenplaats: hoofdrekeningEntity?.heeftKostenplaats?.map(e => e.id.toString()),
          valtbinnenHoofdrekening2: hoofdrekeningEntity?.valtbinnenHoofdrekening2?.id,
          wordtgeschrevenopInkooporders: hoofdrekeningEntity?.wordtgeschrevenopInkooporders?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.hoofdrekening.home.createOrEditLabel" data-cy="HoofdrekeningCreateUpdateHeading">
            Create or edit a Hoofdrekening
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
                <ValidatedField name="id" required readOnly id="hoofdrekening-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Naam" id="hoofdrekening-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField
                label="Nummer"
                id="hoofdrekening-nummer"
                name="nummer"
                data-cy="nummer"
                type="text"
                validate={{
                  maxLength: { value: 8, message: 'This field cannot be longer than 8 characters.' },
                }}
              />
              <ValidatedField label="Omschrijving" id="hoofdrekening-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField
                label="Piahoofcategorieomschrijving"
                id="hoofdrekening-piahoofcategorieomschrijving"
                name="piahoofcategorieomschrijving"
                data-cy="piahoofcategorieomschrijving"
                type="text"
              />
              <ValidatedField
                label="Piahoofdcategoriecode"
                id="hoofdrekening-piahoofdcategoriecode"
                name="piahoofdcategoriecode"
                data-cy="piahoofdcategoriecode"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Subcode"
                id="hoofdrekening-subcode"
                name="subcode"
                data-cy="subcode"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Subcodeomschrijving"
                id="hoofdrekening-subcodeomschrijving"
                name="subcodeomschrijving"
                data-cy="subcodeomschrijving"
                type="text"
              />
              <ValidatedField
                label="Heeft Activa"
                id="hoofdrekening-heeftActiva"
                data-cy="heeftActiva"
                type="select"
                multiple
                name="heeftActivas"
              >
                <option value="" key="0" />
                {activas
                  ? activas.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Heeft Kostenplaats"
                id="hoofdrekening-heeftKostenplaats"
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
              <ValidatedField
                id="hoofdrekening-valtbinnenHoofdrekening2"
                name="valtbinnenHoofdrekening2"
                data-cy="valtbinnenHoofdrekening2"
                label="Valtbinnen Hoofdrekening 2"
                type="select"
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
                label="Wordtgeschrevenop Inkooporder"
                id="hoofdrekening-wordtgeschrevenopInkooporder"
                data-cy="wordtgeschrevenopInkooporder"
                type="select"
                multiple
                name="wordtgeschrevenopInkooporders"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/hoofdrekening" replace color="info">
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

export default HoofdrekeningUpdate;
