import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVerblijfsobject } from 'app/shared/model/verblijfsobject.model';
import { getEntities as getVerblijfsobjects } from 'app/entities/verblijfsobject/verblijfsobject.reducer';
import { IWijk } from 'app/shared/model/wijk.model';
import { getEntities as getWijks } from 'app/entities/wijk/wijk.reducer';
import { IBelijning } from 'app/shared/model/belijning.model';
import { getEntities as getBelijnings } from 'app/entities/belijning/belijning.reducer';
import { ISportmateriaal } from 'app/shared/model/sportmateriaal.model';
import { getEntities as getSportmateriaals } from 'app/entities/sportmateriaal/sportmateriaal.reducer';
import { IBinnenlocatie } from 'app/shared/model/binnenlocatie.model';
import { getEntity, updateEntity, createEntity, reset } from './binnenlocatie.reducer';

export const BinnenlocatieUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const verblijfsobjects = useAppSelector(state => state.verblijfsobject.entities);
  const wijks = useAppSelector(state => state.wijk.entities);
  const belijnings = useAppSelector(state => state.belijning.entities);
  const sportmateriaals = useAppSelector(state => state.sportmateriaal.entities);
  const binnenlocatieEntity = useAppSelector(state => state.binnenlocatie.entity);
  const loading = useAppSelector(state => state.binnenlocatie.loading);
  const updating = useAppSelector(state => state.binnenlocatie.updating);
  const updateSuccess = useAppSelector(state => state.binnenlocatie.updateSuccess);

  const handleClose = () => {
    navigate('/binnenlocatie');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getVerblijfsobjects({}));
    dispatch(getWijks({}));
    dispatch(getBelijnings({}));
    dispatch(getSportmateriaals({}));
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
    if (values.geschattekostenperjaar !== undefined && typeof values.geschattekostenperjaar !== 'number') {
      values.geschattekostenperjaar = Number(values.geschattekostenperjaar);
    }

    const entity = {
      ...binnenlocatieEntity,
      ...values,
      isgevestigdinVerblijfsobject: verblijfsobjects.find(it => it.id.toString() === values.isgevestigdinVerblijfsobject?.toString()),
      bedientWijk: wijks.find(it => it.id.toString() === values.bedientWijk?.toString()),
      heeftBelijnings: mapIdList(values.heeftBelijnings),
      heeftSportmateriaals: mapIdList(values.heeftSportmateriaals),
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
          ...binnenlocatieEntity,
          isgevestigdinVerblijfsobject: binnenlocatieEntity?.isgevestigdinVerblijfsobject?.id,
          bedientWijk: binnenlocatieEntity?.bedientWijk?.id,
          heeftBelijnings: binnenlocatieEntity?.heeftBelijnings?.map(e => e.id.toString()),
          heeftSportmateriaals: binnenlocatieEntity?.heeftSportmateriaals?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.binnenlocatie.home.createOrEditLabel" data-cy="BinnenlocatieCreateUpdateHeading">
            Create or edit a Binnenlocatie
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
                <ValidatedField name="id" required readOnly id="binnenlocatie-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Adres" id="binnenlocatie-adres" name="adres" data-cy="adres" type="text" />
              <ValidatedField label="Bouwjaar" id="binnenlocatie-bouwjaar" name="bouwjaar" data-cy="bouwjaar" type="text" />
              <ValidatedField
                label="Gemeentelijk"
                id="binnenlocatie-gemeentelijk"
                name="gemeentelijk"
                data-cy="gemeentelijk"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Geschattekostenperjaar"
                id="binnenlocatie-geschattekostenperjaar"
                name="geschattekostenperjaar"
                data-cy="geschattekostenperjaar"
                type="text"
              />
              <ValidatedField label="Gymzaal" id="binnenlocatie-gymzaal" name="gymzaal" data-cy="gymzaal" type="text" />
              <ValidatedField
                label="Klokurenonderwijs"
                id="binnenlocatie-klokurenonderwijs"
                name="klokurenonderwijs"
                data-cy="klokurenonderwijs"
                type="text"
              />
              <ValidatedField
                label="Klokurenverenigingen"
                id="binnenlocatie-klokurenverenigingen"
                name="klokurenverenigingen"
                data-cy="klokurenverenigingen"
                type="text"
              />
              <ValidatedField label="Locatie" id="binnenlocatie-locatie" name="locatie" data-cy="locatie" type="text" />
              <ValidatedField
                label="Onderhoudsniveau"
                id="binnenlocatie-onderhoudsniveau"
                name="onderhoudsniveau"
                data-cy="onderhoudsniveau"
                type="text"
              />
              <ValidatedField
                label="Onderhoudsstatus"
                id="binnenlocatie-onderhoudsstatus"
                name="onderhoudsstatus"
                data-cy="onderhoudsstatus"
                type="text"
              />
              <ValidatedField label="Sporthal" id="binnenlocatie-sporthal" name="sporthal" data-cy="sporthal" type="text" />
              <ValidatedField
                label="Vloeroppervlakte"
                id="binnenlocatie-vloeroppervlakte"
                name="vloeroppervlakte"
                data-cy="vloeroppervlakte"
                type="text"
              />
              <ValidatedField
                id="binnenlocatie-isgevestigdinVerblijfsobject"
                name="isgevestigdinVerblijfsobject"
                data-cy="isgevestigdinVerblijfsobject"
                label="Isgevestigdin Verblijfsobject"
                type="select"
              >
                <option value="" key="0" />
                {verblijfsobjects
                  ? verblijfsobjects.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="binnenlocatie-bedientWijk" name="bedientWijk" data-cy="bedientWijk" label="Bedient Wijk" type="select">
                <option value="" key="0" />
                {wijks
                  ? wijks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Heeft Belijning"
                id="binnenlocatie-heeftBelijning"
                data-cy="heeftBelijning"
                type="select"
                multiple
                name="heeftBelijnings"
              >
                <option value="" key="0" />
                {belijnings
                  ? belijnings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Heeft Sportmateriaal"
                id="binnenlocatie-heeftSportmateriaal"
                data-cy="heeftSportmateriaal"
                type="select"
                multiple
                name="heeftSportmateriaals"
              >
                <option value="" key="0" />
                {sportmateriaals
                  ? sportmateriaals.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/binnenlocatie" replace color="info">
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

export default BinnenlocatieUpdate;
