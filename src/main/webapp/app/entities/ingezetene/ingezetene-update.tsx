import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVerblijfstitel } from 'app/shared/model/verblijfstitel.model';
import { getEntities as getVerblijfstitels } from 'app/entities/verblijfstitel/verblijfstitel.reducer';
import { IIngezetene } from 'app/shared/model/ingezetene.model';
import { getEntity, updateEntity, createEntity, reset } from './ingezetene.reducer';

export const IngezeteneUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const verblijfstitels = useAppSelector(state => state.verblijfstitel.entities);
  const ingezeteneEntity = useAppSelector(state => state.ingezetene.entity);
  const loading = useAppSelector(state => state.ingezetene.loading);
  const updating = useAppSelector(state => state.ingezetene.updating);
  const updateSuccess = useAppSelector(state => state.ingezetene.updateSuccess);

  const handleClose = () => {
    navigate('/ingezetene');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getVerblijfstitels({}));
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
      ...ingezeteneEntity,
      ...values,
      heeftVerblijfstitel: verblijfstitels.find(it => it.id.toString() === values.heeftVerblijfstitel?.toString()),
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
          ...ingezeteneEntity,
          heeftVerblijfstitel: ingezeteneEntity?.heeftVerblijfstitel?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.ingezetene.home.createOrEditLabel" data-cy="IngezeteneCreateUpdateHeading">
            Create or edit a Ingezetene
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="ingezetene-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Aanduidingeuropeeskiesrecht"
                id="ingezetene-aanduidingeuropeeskiesrecht"
                name="aanduidingeuropeeskiesrecht"
                data-cy="aanduidingeuropeeskiesrecht"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Aanduidinguitgeslotenkiesrecht"
                id="ingezetene-aanduidinguitgeslotenkiesrecht"
                name="aanduidinguitgeslotenkiesrecht"
                data-cy="aanduidinguitgeslotenkiesrecht"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Datumverkrijgingverblijfstitel"
                id="ingezetene-datumverkrijgingverblijfstitel"
                name="datumverkrijgingverblijfstitel"
                data-cy="datumverkrijgingverblijfstitel"
                type="text"
              />
              <ValidatedField
                label="Datumverliesverblijfstitel"
                id="ingezetene-datumverliesverblijfstitel"
                name="datumverliesverblijfstitel"
                data-cy="datumverliesverblijfstitel"
                type="text"
              />
              <ValidatedField
                label="Indicatieblokkering"
                id="ingezetene-indicatieblokkering"
                name="indicatieblokkering"
                data-cy="indicatieblokkering"
                type="text"
              />
              <ValidatedField
                label="Indicatiecurateleregister"
                id="ingezetene-indicatiecurateleregister"
                name="indicatiecurateleregister"
                data-cy="indicatiecurateleregister"
                type="text"
              />
              <ValidatedField
                label="Indicatiegezagminderjarige"
                id="ingezetene-indicatiegezagminderjarige"
                name="indicatiegezagminderjarige"
                data-cy="indicatiegezagminderjarige"
                type="text"
              />
              <ValidatedField
                id="ingezetene-heeftVerblijfstitel"
                name="heeftVerblijfstitel"
                data-cy="heeftVerblijfstitel"
                label="Heeft Verblijfstitel"
                type="select"
              >
                <option value="" key="0" />
                {verblijfstitels
                  ? verblijfstitels.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/ingezetene" replace color="info">
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

export default IngezeteneUpdate;
