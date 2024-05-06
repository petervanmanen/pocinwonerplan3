import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Erfgoedobject from './erfgoedobject';
import ErfgoedobjectDetail from './erfgoedobject-detail';
import ErfgoedobjectUpdate from './erfgoedobject-update';
import ErfgoedobjectDeleteDialog from './erfgoedobject-delete-dialog';

const ErfgoedobjectRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Erfgoedobject />} />
    <Route path="new" element={<ErfgoedobjectUpdate />} />
    <Route path=":id">
      <Route index element={<ErfgoedobjectDetail />} />
      <Route path="edit" element={<ErfgoedobjectUpdate />} />
      <Route path="delete" element={<ErfgoedobjectDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ErfgoedobjectRoutes;
