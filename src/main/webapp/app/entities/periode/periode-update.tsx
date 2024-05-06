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
import { IArchiefstuk } from 'app/shared/model/archiefstuk.model';
import { getEntities as getArchiefstuks } from 'app/entities/archiefstuk/archiefstuk.reducer';
import { IHoofdstuk } from 'app/shared/model/hoofdstuk.model';
import { getEntities as getHoofdstuks } from 'app/entities/hoofdstuk/hoofdstuk.reducer';
import { IPeriode } from 'app/shared/model/periode.model';
import { getEntity, updateEntity, createEntity, reset } from './periode.reducer';

export const PeriodeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const archiefs = useAppSelector(state => state.archief.entities);
  const archiefstuks = useAppSelector(state => state.archiefstuk.entities);
  const hoofdstuks = useAppSelector(state => state.hoofdstuk.entities);
  const periodeEntity = useAppSelector(state => state.periode.entity);
  const loading = useAppSelector(state => state.periode.loading);
  const updating = useAppSelector(state => state.periode.updating);
  const updateSuccess = useAppSelector(state => state.periode.updateSuccess);

  const handleClose = () => {
    navigate('/periode');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getArchiefs({}));
    dispatch(getArchiefstuks({}));
    dispatch(getHoofdstuks({}));
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
      ...periodeEntity,
      ...values,
      stamtuitArchiefs: mapIdList(values.stamtuitArchiefs),
      stamtuitArchiefstuks: mapIdList(values.stamtuitArchiefstuks),
      binnenHoofdstuks: mapIdList(values.binnenHoofdstuks),
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
          ...periodeEntity,
          stamtuitArchiefs: periodeEntity?.stamtuitArchiefs?.map(e => e.id.toString()),
          stamtuitArchiefstuks: periodeEntity?.stamtuitArchiefstuks?.map(e => e.id.toString()),
          binnenHoofdstuks: periodeEntity?.binnenHoofdstuks?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.periode.home.createOrEditLabel" data-cy="PeriodeCreateUpdateHeading">
            Create or edit a Periode
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="periode-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Datumeinde" id="periode-datumeinde" name="datumeinde" data-cy="datumeinde" type="text" />
              <ValidatedField label="Datumstart" id="periode-datumstart" name="datumstart" data-cy="datumstart" type="text" />
              <ValidatedField label="Omschrijving" id="periode-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField
                label="Stamtuit Archief"
                id="periode-stamtuitArchief"
                data-cy="stamtuitArchief"
                type="select"
                multiple
                name="stamtuitArchiefs"
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
                label="Stamtuit Archiefstuk"
                id="periode-stamtuitArchiefstuk"
                data-cy="stamtuitArchiefstuk"
                type="select"
                multiple
                name="stamtuitArchiefstuks"
              >
                <option value="" key="0" />
                {archiefstuks
                  ? archiefstuks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Binnen Hoofdstuk"
                id="periode-binnenHoofdstuk"
                data-cy="binnenHoofdstuk"
                type="select"
                multiple
                name="binnenHoofdstuks"
              >
                <option value="" key="0" />
                {hoofdstuks
                  ? hoofdstuks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/periode" replace color="info">
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

export default PeriodeUpdate;
