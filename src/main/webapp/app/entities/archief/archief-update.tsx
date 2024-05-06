import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRechthebbende } from 'app/shared/model/rechthebbende.model';
import { getEntities as getRechthebbendes } from 'app/entities/rechthebbende/rechthebbende.reducer';
import { IArchiefcategorie } from 'app/shared/model/archiefcategorie.model';
import { getEntities as getArchiefcategories } from 'app/entities/archiefcategorie/archiefcategorie.reducer';
import { IPeriode } from 'app/shared/model/periode.model';
import { getEntities as getPeriodes } from 'app/entities/periode/periode.reducer';
import { IArchief } from 'app/shared/model/archief.model';
import { getEntity, updateEntity, createEntity, reset } from './archief.reducer';

export const ArchiefUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const rechthebbendes = useAppSelector(state => state.rechthebbende.entities);
  const archiefcategories = useAppSelector(state => state.archiefcategorie.entities);
  const periodes = useAppSelector(state => state.periode.entities);
  const archiefEntity = useAppSelector(state => state.archief.entity);
  const loading = useAppSelector(state => state.archief.loading);
  const updating = useAppSelector(state => state.archief.updating);
  const updateSuccess = useAppSelector(state => state.archief.updateSuccess);

  const handleClose = () => {
    navigate('/archief');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getRechthebbendes({}));
    dispatch(getArchiefcategories({}));
    dispatch(getPeriodes({}));
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
      ...archiefEntity,
      ...values,
      heeftRechthebbende: rechthebbendes.find(it => it.id.toString() === values.heeftRechthebbende?.toString()),
      valtbinnenArchiefcategories: mapIdList(values.valtbinnenArchiefcategories),
      stamtuitPeriodes: mapIdList(values.stamtuitPeriodes),
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
          ...archiefEntity,
          heeftRechthebbende: archiefEntity?.heeftRechthebbende?.id,
          valtbinnenArchiefcategories: archiefEntity?.valtbinnenArchiefcategories?.map(e => e.id.toString()),
          stamtuitPeriodes: archiefEntity?.stamtuitPeriodes?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.archief.home.createOrEditLabel" data-cy="ArchiefCreateUpdateHeading">
            Create or edit a Archief
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="archief-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Archiefnummer"
                id="archief-archiefnummer"
                name="archiefnummer"
                data-cy="archiefnummer"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField label="Naam" id="archief-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField label="Omschrijving" id="archief-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField
                label="Openbaarheidsbeperking"
                id="archief-openbaarheidsbeperking"
                name="openbaarheidsbeperking"
                data-cy="openbaarheidsbeperking"
                type="text"
              />
              <ValidatedField
                id="archief-heeftRechthebbende"
                name="heeftRechthebbende"
                data-cy="heeftRechthebbende"
                label="Heeft Rechthebbende"
                type="select"
              >
                <option value="" key="0" />
                {rechthebbendes
                  ? rechthebbendes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Valtbinnen Archiefcategorie"
                id="archief-valtbinnenArchiefcategorie"
                data-cy="valtbinnenArchiefcategorie"
                type="select"
                multiple
                name="valtbinnenArchiefcategories"
              >
                <option value="" key="0" />
                {archiefcategories
                  ? archiefcategories.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Stamtuit Periode"
                id="archief-stamtuitPeriode"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/archief" replace color="info">
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

export default ArchiefUpdate;
