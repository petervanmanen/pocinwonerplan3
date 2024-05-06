import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDatatype } from 'app/shared/model/datatype.model';
import { getEntities as getDatatypes } from 'app/entities/datatype/datatype.reducer';
import { IAttribuutsoort } from 'app/shared/model/attribuutsoort.model';
import { getEntity, updateEntity, createEntity, reset } from './attribuutsoort.reducer';

export const AttribuutsoortUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const datatypes = useAppSelector(state => state.datatype.entities);
  const attribuutsoortEntity = useAppSelector(state => state.attribuutsoort.entity);
  const loading = useAppSelector(state => state.attribuutsoort.loading);
  const updating = useAppSelector(state => state.attribuutsoort.updating);
  const updateSuccess = useAppSelector(state => state.attribuutsoort.updateSuccess);

  const handleClose = () => {
    navigate('/attribuutsoort');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getDatatypes({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    const entity = {
      ...attribuutsoortEntity,
      ...values,
      heeftDatatype: datatypes.find(it => it.id.toString() === values.heeftDatatype?.toString()),
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
          ...attribuutsoortEntity,
          heeftDatatype: attribuutsoortEntity?.heeftDatatype?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.attribuutsoort.home.createOrEditLabel" data-cy="AttribuutsoortCreateUpdateHeading">
            Create or edit a Attribuutsoort
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
                <ValidatedField name="id" required readOnly id="attribuutsoort-id" label="Id" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Authentiek"
                id="attribuutsoort-authentiek"
                name="authentiek"
                data-cy="authentiek"
                check
                type="checkbox"
              />
              <ValidatedField label="Datumopname" id="attribuutsoort-datumopname" name="datumopname" data-cy="datumopname" type="date" />
              <ValidatedField label="Definitie" id="attribuutsoort-definitie" name="definitie" data-cy="definitie" type="text" />
              <ValidatedField label="Domein" id="attribuutsoort-domein" name="domein" data-cy="domein" type="text" />
              <ValidatedField label="Eaguid" id="attribuutsoort-eaguid" name="eaguid" data-cy="eaguid" type="text" />
              <ValidatedField label="Herkomst" id="attribuutsoort-herkomst" name="herkomst" data-cy="herkomst" type="text" />
              <ValidatedField
                label="Herkomstdefinitie"
                id="attribuutsoort-herkomstdefinitie"
                name="herkomstdefinitie"
                data-cy="herkomstdefinitie"
                type="text"
              />
              <ValidatedField
                label="Identificerend"
                id="attribuutsoort-identificerend"
                name="identificerend"
                data-cy="identificerend"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Indicatieafleidbaar"
                id="attribuutsoort-indicatieafleidbaar"
                name="indicatieafleidbaar"
                data-cy="indicatieafleidbaar"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Indicatiematerielehistorie"
                id="attribuutsoort-indicatiematerielehistorie"
                name="indicatiematerielehistorie"
                data-cy="indicatiematerielehistorie"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Kardinaliteit"
                id="attribuutsoort-kardinaliteit"
                name="kardinaliteit"
                data-cy="kardinaliteit"
                type="text"
              />
              <ValidatedField label="Lengte" id="attribuutsoort-lengte" name="lengte" data-cy="lengte" type="text" />
              <ValidatedField
                label="Mogelijkgeenwaarde"
                id="attribuutsoort-mogelijkgeenwaarde"
                name="mogelijkgeenwaarde"
                data-cy="mogelijkgeenwaarde"
                check
                type="checkbox"
              />
              <ValidatedField label="Naam" id="attribuutsoort-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField label="Patroon" id="attribuutsoort-patroon" name="patroon" data-cy="patroon" type="text" />
              <ValidatedField label="Precisie" id="attribuutsoort-precisie" name="precisie" data-cy="precisie" type="text" />
              <ValidatedField label="Stereotype" id="attribuutsoort-stereotype" name="stereotype" data-cy="stereotype" type="text" />
              <ValidatedField label="Toelichting" id="attribuutsoort-toelichting" name="toelichting" data-cy="toelichting" type="text" />
              <ValidatedField
                id="attribuutsoort-heeftDatatype"
                name="heeftDatatype"
                data-cy="heeftDatatype"
                label="Heeft Datatype"
                type="select"
              >
                <option value="" key="0" />
                {datatypes
                  ? datatypes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/attribuutsoort" replace color="info">
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

export default AttribuutsoortUpdate;
