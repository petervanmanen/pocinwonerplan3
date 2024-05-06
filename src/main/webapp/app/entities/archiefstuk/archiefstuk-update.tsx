import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IArchief } from 'app/shared/model/archief.model';
import { getEntities as getArchiefs } from 'app/entities/archief/archief.reducer';
import { IUitgever } from 'app/shared/model/uitgever.model';
import { getEntities as getUitgevers } from 'app/entities/uitgever/uitgever.reducer';
import { IVindplaats } from 'app/shared/model/vindplaats.model';
import { getEntities as getVindplaats } from 'app/entities/vindplaats/vindplaats.reducer';
import { IOrdeningsschema } from 'app/shared/model/ordeningsschema.model';
import { getEntities as getOrdeningsschemas } from 'app/entities/ordeningsschema/ordeningsschema.reducer';
import { IPeriode } from 'app/shared/model/periode.model';
import { getEntities as getPeriodes } from 'app/entities/periode/periode.reducer';
import { IIndeling } from 'app/shared/model/indeling.model';
import { getEntities as getIndelings } from 'app/entities/indeling/indeling.reducer';
import { IAanvraag } from 'app/shared/model/aanvraag.model';
import { getEntities as getAanvraags } from 'app/entities/aanvraag/aanvraag.reducer';
import { IArchiefstuk } from 'app/shared/model/archiefstuk.model';
import { getEntity, updateEntity, createEntity, reset } from './archiefstuk.reducer';

export const ArchiefstukUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const archiefs = useAppSelector(state => state.archief.entities);
  const uitgevers = useAppSelector(state => state.uitgever.entities);
  const vindplaats = useAppSelector(state => state.vindplaats.entities);
  const ordeningsschemas = useAppSelector(state => state.ordeningsschema.entities);
  const periodes = useAppSelector(state => state.periode.entities);
  const indelings = useAppSelector(state => state.indeling.entities);
  const aanvraags = useAppSelector(state => state.aanvraag.entities);
  const archiefstukEntity = useAppSelector(state => state.archiefstuk.entity);
  const loading = useAppSelector(state => state.archiefstuk.loading);
  const updating = useAppSelector(state => state.archiefstuk.updating);
  const updateSuccess = useAppSelector(state => state.archiefstuk.updateSuccess);

  const handleClose = () => {
    navigate('/archiefstuk');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getArchiefs({}));
    dispatch(getUitgevers({}));
    dispatch(getVindplaats({}));
    dispatch(getOrdeningsschemas({}));
    dispatch(getPeriodes({}));
    dispatch(getIndelings({}));
    dispatch(getAanvraags({}));
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
      ...archiefstukEntity,
      ...values,
      isonderdeelvanArchief: archiefs.find(it => it.id.toString() === values.isonderdeelvanArchief?.toString()),
      heeftUitgever: uitgevers.find(it => it.id.toString() === values.heeftUitgever?.toString()),
      heeftVindplaats: vindplaats.find(it => it.id.toString() === values.heeftVindplaats?.toString()),
      heeftOrdeningsschemas: mapIdList(values.heeftOrdeningsschemas),
      stamtuitPeriodes: mapIdList(values.stamtuitPeriodes),
      valtbinnenIndeling: indelings.find(it => it.id.toString() === values.valtbinnenIndeling?.toString()),
      voorAanvraags: mapIdList(values.voorAanvraags),
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
          ...archiefstukEntity,
          isonderdeelvanArchief: archiefstukEntity?.isonderdeelvanArchief?.id,
          heeftUitgever: archiefstukEntity?.heeftUitgever?.id,
          heeftVindplaats: archiefstukEntity?.heeftVindplaats?.id,
          heeftOrdeningsschemas: archiefstukEntity?.heeftOrdeningsschemas?.map(e => e.id.toString()),
          stamtuitPeriodes: archiefstukEntity?.stamtuitPeriodes?.map(e => e.id.toString()),
          valtbinnenIndeling: archiefstukEntity?.valtbinnenIndeling?.id,
          voorAanvraags: archiefstukEntity?.voorAanvraags?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.archiefstuk.home.createOrEditLabel" data-cy="ArchiefstukCreateUpdateHeading">
            Create or edit a Archiefstuk
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="archiefstuk-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Beschrijving" id="archiefstuk-beschrijving" name="beschrijving" data-cy="beschrijving" type="text" />
              <ValidatedField
                label="Inventarisnummer"
                id="archiefstuk-inventarisnummer"
                name="inventarisnummer"
                data-cy="inventarisnummer"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField label="Omvang" id="archiefstuk-omvang" name="omvang" data-cy="omvang" type="text" />
              <ValidatedField
                label="Openbaarheidsbeperking"
                id="archiefstuk-openbaarheidsbeperking"
                name="openbaarheidsbeperking"
                data-cy="openbaarheidsbeperking"
                type="text"
              />
              <ValidatedField label="Trefwoorden" id="archiefstuk-trefwoorden" name="trefwoorden" data-cy="trefwoorden" type="text" />
              <ValidatedField
                label="Uiterlijkevorm"
                id="archiefstuk-uiterlijkevorm"
                name="uiterlijkevorm"
                data-cy="uiterlijkevorm"
                type="text"
              />
              <ValidatedField
                id="archiefstuk-isonderdeelvanArchief"
                name="isonderdeelvanArchief"
                data-cy="isonderdeelvanArchief"
                label="Isonderdeelvan Archief"
                type="select"
              >
                <option value="" key="0" />
                {archiefs
                  ? archiefs.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="archiefstuk-heeftUitgever"
                name="heeftUitgever"
                data-cy="heeftUitgever"
                label="Heeft Uitgever"
                type="select"
              >
                <option value="" key="0" />
                {uitgevers
                  ? uitgevers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="archiefstuk-heeftVindplaats"
                name="heeftVindplaats"
                data-cy="heeftVindplaats"
                label="Heeft Vindplaats"
                type="select"
              >
                <option value="" key="0" />
                {vindplaats
                  ? vindplaats.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Heeft Ordeningsschema"
                id="archiefstuk-heeftOrdeningsschema"
                data-cy="heeftOrdeningsschema"
                type="select"
                multiple
                name="heeftOrdeningsschemas"
              >
                <option value="" key="0" />
                {ordeningsschemas
                  ? ordeningsschemas.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Stamtuit Periode"
                id="archiefstuk-stamtuitPeriode"
                data-cy="stamtuitPeriode"
                type="select"
                multiple
                name="stamtuitPeriodes"
              >
                <option value="" key="0" />
                {periodes
                  ? periodes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="archiefstuk-valtbinnenIndeling"
                name="valtbinnenIndeling"
                data-cy="valtbinnenIndeling"
                label="Valtbinnen Indeling"
                type="select"
              >
                <option value="" key="0" />
                {indelings
                  ? indelings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Voor Aanvraag"
                id="archiefstuk-voorAanvraag"
                data-cy="voorAanvraag"
                type="select"
                multiple
                name="voorAanvraags"
              >
                <option value="" key="0" />
                {aanvraags
                  ? aanvraags.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/archiefstuk" replace color="info">
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

export default ArchiefstukUpdate;
