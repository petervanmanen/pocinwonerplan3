import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISelectietabelaanbesteding } from 'app/shared/model/selectietabelaanbesteding.model';
import { getEntity, updateEntity, createEntity, reset } from './selectietabelaanbesteding.reducer';

export const SelectietabelaanbestedingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const selectietabelaanbestedingEntity = useAppSelector(state => state.selectietabelaanbesteding.entity);
  const loading = useAppSelector(state => state.selectietabelaanbesteding.loading);
  const updating = useAppSelector(state => state.selectietabelaanbesteding.updating);
  const updateSuccess = useAppSelector(state => state.selectietabelaanbesteding.updateSuccess);

  const handleClose = () => {
    navigate('/selectietabelaanbesteding');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
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
    if (values.drempelbedragtot !== undefined && typeof values.drempelbedragtot !== 'number') {
      values.drempelbedragtot = Number(values.drempelbedragtot);
    }
    if (values.drempelbedragvanaf !== undefined && typeof values.drempelbedragvanaf !== 'number') {
      values.drempelbedragvanaf = Number(values.drempelbedragvanaf);
    }

    const entity = {
      ...selectietabelaanbestedingEntity,
      ...values,
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
          ...selectietabelaanbestedingEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.selectietabelaanbesteding.home.createOrEditLabel" data-cy="SelectietabelaanbestedingCreateUpdateHeading">
            Create or edit a Selectietabelaanbesteding
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
                <ValidatedField name="id" required readOnly id="selectietabelaanbesteding-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Aanbestedingsoort"
                id="selectietabelaanbesteding-aanbestedingsoort"
                name="aanbestedingsoort"
                data-cy="aanbestedingsoort"
                type="text"
              />
              <ValidatedField
                label="Drempelbedragtot"
                id="selectietabelaanbesteding-drempelbedragtot"
                name="drempelbedragtot"
                data-cy="drempelbedragtot"
                type="text"
              />
              <ValidatedField
                label="Drempelbedragvanaf"
                id="selectietabelaanbesteding-drempelbedragvanaf"
                name="drempelbedragvanaf"
                data-cy="drempelbedragvanaf"
                type="text"
              />
              <ValidatedField
                label="Opdrachtcategorie"
                id="selectietabelaanbesteding-opdrachtcategorie"
                name="opdrachtcategorie"
                data-cy="opdrachtcategorie"
                type="text"
              />
              <ValidatedField label="Openbaar" id="selectietabelaanbesteding-openbaar" name="openbaar" data-cy="openbaar" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/selectietabelaanbesteding" replace color="info">
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

export default SelectietabelaanbestedingUpdate;
