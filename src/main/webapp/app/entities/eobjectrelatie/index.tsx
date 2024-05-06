import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Eobjectrelatie from './eobjectrelatie';
import EobjectrelatieDetail from './eobjectrelatie-detail';
import EobjectrelatieUpdate from './eobjectrelatie-update';
import EobjectrelatieDeleteDialog from './eobjectrelatie-delete-dialog';

const EobjectrelatieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Eobjectrelatie />} />
    <Route path="new" element={<EobjectrelatieUpdate />} />
    <Route path=":id">
      <Route index element={<EobjectrelatieDetail />} />
      <Route path="edit" element={<EobjectrelatieUpdate />} />
      <Route path="delete" element={<EobjectrelatieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EobjectrelatieRoutes;
