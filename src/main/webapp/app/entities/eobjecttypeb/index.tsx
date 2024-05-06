import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Eobjecttypeb from './eobjecttypeb';
import EobjecttypebDetail from './eobjecttypeb-detail';
import EobjecttypebUpdate from './eobjecttypeb-update';
import EobjecttypebDeleteDialog from './eobjecttypeb-delete-dialog';

const EobjecttypebRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Eobjecttypeb />} />
    <Route path="new" element={<EobjecttypebUpdate />} />
    <Route path=":id">
      <Route index element={<EobjecttypebDetail />} />
      <Route path="edit" element={<EobjecttypebUpdate />} />
      <Route path="delete" element={<EobjecttypebDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EobjecttypebRoutes;
