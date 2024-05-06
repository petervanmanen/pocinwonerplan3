import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IArchiefstuk } from 'app/shared/model/archiefstuk.model';
import { getEntities as getArchiefstuks } from 'app/entities/archiefstuk/archiefstuk.reducer';
import { IOrdeningsschema } from 'app/shared/model/ordeningsschema.model';
import { getEntity, updateEntity, createEntity, reset } from './ordeningsschema.reducer';

export const OrdeningsschemaUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const archiefstuks = useAppSelector(state => state.archiefstuk.entities);
  const ordeningsschemaEntity = useAppSelector(state => state.ordeningsschema.entity);
  const loading = useAppSelector(state => state.ordeningsschema.loading);
  const updating = useAppSelector(state => state.ordeningsschema.updating);
  const updateSuccess = useAppSelector(state => state.ordeningsschema.updateSuccess);

  const handleClose = () => {
    navigate('/ordeningsschema');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getArchiefstuks({}));
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
      ...ordeningsschemaEntity,
      ...values,
      heeftArchiefstuks: mapIdList(values.heeftArchiefstuks),
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
          ...ordeningsschemaEntity,
          heeftArchiefstuks: ordeningsschemaEntity?.heeftArchiefstuks?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.ordeningsschema.home.createOrEditLabel" data-cy="OrdeningsschemaCreateUpdateHeading">
            Create or edit a Ordeningsschema
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
                <ValidatedField name="id" required readOnly id="ordeningsschema-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Naam" id="ordeningsschema-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField label="Text" id="ordeningsschema-text" name="text" data-cy="text" type="text" />
              <ValidatedField
                label="Heeft Archiefstuk"
                id="ordeningsschema-heeftArchiefstuk"
                data-cy="heeftArchiefstuk"
                type="select"
                multiple
                name="heeftArchiefstuks"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/ordeningsschema" replace color="info">
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

export default OrdeningsschemaUpdate;
