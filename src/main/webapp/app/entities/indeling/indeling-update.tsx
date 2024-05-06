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
import { getEntities as getIndelings } from 'app/entities/indeling/indeling.reducer';
import { IIndeling } from 'app/shared/model/indeling.model';
import { getEntity, updateEntity, createEntity, reset } from './indeling.reducer';

export const IndelingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const archiefs = useAppSelector(state => state.archief.entities);
  const indelings = useAppSelector(state => state.indeling.entities);
  const indelingEntity = useAppSelector(state => state.indeling.entity);
  const loading = useAppSelector(state => state.indeling.loading);
  const updating = useAppSelector(state => state.indeling.updating);
  const updateSuccess = useAppSelector(state => state.indeling.updateSuccess);

  const handleClose = () => {
    navigate('/indeling');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getArchiefs({}));
    dispatch(getIndelings({}));
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
      ...indelingEntity,
      ...values,
      hoortbijArchief: archiefs.find(it => it.id.toString() === values.hoortbijArchief?.toString()),
      valtbinnenIndeling2: indelings.find(it => it.id.toString() === values.valtbinnenIndeling2?.toString()),
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
          ...indelingEntity,
          hoortbijArchief: indelingEntity?.hoortbijArchief?.id,
          valtbinnenIndeling2: indelingEntity?.valtbinnenIndeling2?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.indeling.home.createOrEditLabel" data-cy="IndelingCreateUpdateHeading">
            Create or edit a Indeling
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="indeling-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Indelingsoort"
                id="indeling-indelingsoort"
                name="indelingsoort"
                data-cy="indelingsoort"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField label="Naam" id="indeling-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField
                label="Nummer"
                id="indeling-nummer"
                name="nummer"
                data-cy="nummer"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField label="Omschrijving" id="indeling-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField
                id="indeling-hoortbijArchief"
                name="hoortbijArchief"
                data-cy="hoortbijArchief"
                label="Hoortbij Archief"
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
                id="indeling-valtbinnenIndeling2"
                name="valtbinnenIndeling2"
                data-cy="valtbinnenIndeling2"
                label="Valtbinnen Indeling 2"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/indeling" replace color="info">
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

export default IndelingUpdate;
