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
import { IArchiefcategorie } from 'app/shared/model/archiefcategorie.model';
import { getEntity, updateEntity, createEntity, reset } from './archiefcategorie.reducer';

export const ArchiefcategorieUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const archiefs = useAppSelector(state => state.archief.entities);
  const archiefcategorieEntity = useAppSelector(state => state.archiefcategorie.entity);
  const loading = useAppSelector(state => state.archiefcategorie.loading);
  const updating = useAppSelector(state => state.archiefcategorie.updating);
  const updateSuccess = useAppSelector(state => state.archiefcategorie.updateSuccess);

  const handleClose = () => {
    navigate('/archiefcategorie');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getArchiefs({}));
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
      ...archiefcategorieEntity,
      ...values,
      valtbinnenArchiefs: mapIdList(values.valtbinnenArchiefs),
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
          ...archiefcategorieEntity,
          valtbinnenArchiefs: archiefcategorieEntity?.valtbinnenArchiefs?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.archiefcategorie.home.createOrEditLabel" data-cy="ArchiefcategorieCreateUpdateHeading">
            Create or edit a Archiefcategorie
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
                <ValidatedField name="id" required readOnly id="archiefcategorie-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Naam" id="archiefcategorie-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField
                label="Nummer"
                id="archiefcategorie-nummer"
                name="nummer"
                data-cy="nummer"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Omschrijving"
                id="archiefcategorie-omschrijving"
                name="omschrijving"
                data-cy="omschrijving"
                type="text"
              />
              <ValidatedField
                label="Valtbinnen Archief"
                id="archiefcategorie-valtbinnenArchief"
                data-cy="valtbinnenArchief"
                type="select"
                multiple
                name="valtbinnenArchiefs"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/archiefcategorie" replace color="info">
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

export default ArchiefcategorieUpdate;
