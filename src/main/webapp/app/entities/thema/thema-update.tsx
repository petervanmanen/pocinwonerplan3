import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getThemas } from 'app/entities/thema/thema.reducer';
import { IRegeltekst } from 'app/shared/model/regeltekst.model';
import { getEntities as getRegelteksts } from 'app/entities/regeltekst/regeltekst.reducer';
import { IThema } from 'app/shared/model/thema.model';
import { getEntity, updateEntity, createEntity, reset } from './thema.reducer';

export const ThemaUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const themas = useAppSelector(state => state.thema.entities);
  const regelteksts = useAppSelector(state => state.regeltekst.entities);
  const themaEntity = useAppSelector(state => state.thema.entity);
  const loading = useAppSelector(state => state.thema.loading);
  const updating = useAppSelector(state => state.thema.updating);
  const updateSuccess = useAppSelector(state => state.thema.updateSuccess);

  const handleClose = () => {
    navigate('/thema');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getThemas({}));
    dispatch(getRegelteksts({}));
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
      ...themaEntity,
      ...values,
      subthemaThema2: themas.find(it => it.id.toString() === values.subthemaThema2?.toString()),
      heeftthemaRegelteksts: mapIdList(values.heeftthemaRegelteksts),
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
          ...themaEntity,
          subthemaThema2: themaEntity?.subthemaThema2?.id,
          heeftthemaRegelteksts: themaEntity?.heeftthemaRegelteksts?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.thema.home.createOrEditLabel" data-cy="ThemaCreateUpdateHeading">
            Create or edit a Thema
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="thema-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Naam" id="thema-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField label="Omschrijving" id="thema-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField
                id="thema-subthemaThema2"
                name="subthemaThema2"
                data-cy="subthemaThema2"
                label="Subthema Thema 2"
                type="select"
              >
                <option value="" key="0" />
                {themas
                  ? themas.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Heeftthema Regeltekst"
                id="thema-heeftthemaRegeltekst"
                data-cy="heeftthemaRegeltekst"
                type="select"
                multiple
                name="heeftthemaRegelteksts"
              >
                <option value="" key="0" />
                {regelteksts
                  ? regelteksts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/thema" replace color="info">
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

export default ThemaUpdate;
